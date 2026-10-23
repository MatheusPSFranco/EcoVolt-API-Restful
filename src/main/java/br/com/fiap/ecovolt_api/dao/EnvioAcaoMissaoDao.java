package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import br.com.fiap.ecovolt_api.model.EnvioAcaoMissao;
import br.com.fiap.ecovolt_api.model.Missao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnvioAcaoMissaoDao {

    private final DataSource dataSource;

    public EnvioAcaoMissaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<EnvioAcaoMissao> listar() {
        List<EnvioAcaoMissao> lista = new ArrayList<>();

        String sql = """
                SELECT id_envio, id_missao,
                       ct_env_aca_mis, dt_contabilizacao
                FROM T_EV_ENV_ACA_MIS
                ORDER BY id_envio, id_missao
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAssociacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar associações entre envios e missões.", e
            );
        }

        return lista;
    }

    public EnvioAcaoMissao buscar(long idEnvio, long idMissao) {
        String sql = """
                SELECT id_envio, id_missao,
                       ct_env_aca_mis, dt_contabilizacao
                FROM T_EV_ENV_ACA_MIS
                WHERE id_envio = ? AND id_missao = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idEnvio);
            stmt.setLong(2, idMissao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAssociacao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar associação entre envio e missão.", e
            );
        }

        throw new EntidadeNaoEncontradaException(
                "Associação entre envio e missão não encontrada."
        );
    }

    public EnvioAcaoMissao inserir(EnvioAcaoMissao associacao) {
        validarAssociacao(associacao);

        String sql = """
                INSERT INTO T_EV_ENV_ACA_MIS (
                    id_envio,
                    id_missao,
                    ct_env_aca_mis,
                    dt_contabilizacao
                ) VALUES (?, ?, NVL(?, 'N'), ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, associacao.getEnvioAcao().getId());
            stmt.setLong(2, associacao.getMissao().getId());

            stmt.setString(3, associacao.getContabilizado());

            if (associacao.getDataContabilizacao() != null) {
                stmt.setTimestamp(
                        4,
                        Timestamp.valueOf(
                                associacao.getDataContabilizacao()
                        )
                );
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            stmt.executeUpdate();

            return associacao;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir associação entre envio e missão.", e
            );
        }
    }

    public void atualizar(
            long idEnvio,
            long idMissao,
            EnvioAcaoMissao associacao) {

        if (associacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da associação devem ser informados."
            );
        }

        String sql = """
                UPDATE T_EV_ENV_ACA_MIS
                SET ct_env_aca_mis = NVL(?, 'N'),
                    dt_contabilizacao = ?
                WHERE id_envio = ? AND id_missao = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, associacao.getContabilizado());

            if (associacao.getDataContabilizacao() != null) {
                stmt.setTimestamp(
                        2,
                        Timestamp.valueOf(
                                associacao.getDataContabilizacao()
                        )
                );
            } else {
                stmt.setNull(2, Types.TIMESTAMP);
            }

            stmt.setLong(3, idEnvio);
            stmt.setLong(4, idMissao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre envio e missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar associação entre envio e missão.", e
            );
        }
    }

    public void deletar(long idEnvio, long idMissao) {
        String sql = """
                DELETE FROM T_EV_ENV_ACA_MIS
                WHERE id_envio = ? AND id_missao = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idEnvio);
            stmt.setLong(2, idMissao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre envio e missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar associação entre envio e missão.", e
            );
        }
    }

    private EnvioAcaoMissao mapearAssociacao(ResultSet rs)
            throws SQLException {

        EnvioAcaoMissao associacao = new EnvioAcaoMissao();

        EnvioAcao envio = new EnvioAcao();
        envio.setId(rs.getLong("id_envio"));

        Missao missao = new Missao();
        missao.setId(rs.getLong("id_missao"));

        associacao.setEnvioAcao(envio);
        associacao.setMissao(missao);
        associacao.setContabilizado(
                rs.getString("ct_env_aca_mis")
        );

        Timestamp timestamp = rs.getTimestamp("dt_contabilizacao");

        associacao.setDataContabilizacao(
                timestamp != null
                        ? timestamp.toLocalDateTime()
                        : null
        );

        return associacao;
    }

    private void validarAssociacao(EnvioAcaoMissao associacao) {
        if (associacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da associação devem ser informados."
            );
        }

        if (associacao.getEnvioAcao() == null
                || associacao.getEnvioAcao().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do envio é obrigatório."
            );
        }

        if (associacao.getMissao() == null
                || associacao.getMissao().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da missão é obrigatório."
            );
        }
    }
}