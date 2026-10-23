package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Missao;
import br.com.fiap.ecovolt_api.model.Ods;
import br.com.fiap.ecovolt_api.model.OdsMissao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdsMissaoDao {

    private final DataSource dataSource;

    public OdsMissaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<OdsMissao> listar() {
        String sql = """
                SELECT id_ods, id_missao, ps_ods
                FROM T_EV_ODS_MIS
                """;

        List<OdsMissao> associacoes = new ArrayList<>();

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                associacoes.add(mapearAssociacao(rs));
            }

            return associacoes;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar associações entre ODS e missões.", e
            );
        }
    }

    public OdsMissao buscar(long idOds, long idMissao) {
        String sql = """
                SELECT id_ods, id_missao, ps_ods
                FROM T_EV_ODS_MIS
                WHERE id_ods = ? AND id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, idOds);
            stmt.setLong(2, idMissao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAssociacao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar associação entre ODS e missão.", e
            );
        }

        throw new EntidadeNaoEncontradaException(
                "Associação entre ODS e missão não encontrada."
        );
    }

    public OdsMissao inserir(OdsMissao associacao) {
        validarAssociacao(associacao);

        String sql = """
                INSERT INTO T_EV_ODS_MIS (
                    id_ods,
                    id_missao,
                    ps_ods
                ) VALUES (?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, associacao.getOds().getId());
            stmt.setLong(2, associacao.getMissao().getId());
            stmt.setDouble(3, associacao.getPesoOds());

            stmt.executeUpdate();

            return associacao;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir associação entre ODS e missão.", e
            );
        }
    }

    public void atualizar(
            long idOds,
            long idMissao,
            OdsMissao associacao) {

        validarAssociacao(associacao);

        String sql = """
                UPDATE T_EV_ODS_MIS
                SET ps_ods = ?
                WHERE id_ods = ? AND id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, associacao.getPesoOds());
            stmt.setLong(2, idOds);
            stmt.setLong(3, idMissao);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre ODS e missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar associação entre ODS e missão.", e
            );
        }
    }

    public void deletar(long idOds, long idMissao) {
        String sql = """
                DELETE FROM T_EV_ODS_MIS
                WHERE id_ods = ? AND id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, idOds);
            stmt.setLong(2, idMissao);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre ODS e missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar associação entre ODS e missão.", e
            );
        }
    }

    private OdsMissao mapearAssociacao(ResultSet rs)
            throws SQLException {

        OdsMissao associacao = new OdsMissao();

        Ods ods = new Ods();
        ods.setId(rs.getLong("id_ods"));

        Missao missao = new Missao();
        missao.setId(rs.getLong("id_missao"));

        associacao.setOds(ods);
        associacao.setMissao(missao);
        associacao.setPesoOds(rs.getDouble("ps_ods"));

        return associacao;
    }

    private void validarAssociacao(OdsMissao associacao) {
        if (associacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da associação são obrigatórios."
            );
        }

        if (associacao.getOds() == null
                || associacao.getOds().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da ODS é obrigatório."
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