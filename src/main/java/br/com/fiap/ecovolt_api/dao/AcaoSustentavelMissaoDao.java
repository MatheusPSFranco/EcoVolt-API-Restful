package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import br.com.fiap.ecovolt_api.model.AcaoSustentavelMissao;
import br.com.fiap.ecovolt_api.model.Missao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AcaoSustentavelMissaoDao {

    private final DataSource dataSource;

    public AcaoSustentavelMissaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<AcaoSustentavelMissao> listar() {
        List<AcaoSustentavelMissao> associacoes = new ArrayList<>();

        String sql = """
                SELECT id_acao, id_missao, qt_necessaria,
                       ob_aca_sus_mis
                FROM T_EV_ACA_SUS_MIS
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                associacoes.add(mapearAssociacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar associações entre ações e missões.", e
            );
        }

        return associacoes;
    }

    public AcaoSustentavelMissao buscar(long idAcao, long idMissao) {
        String sql = """
                SELECT id_acao, id_missao, qt_necessaria,
                       ob_aca_sus_mis
                FROM T_EV_ACA_SUS_MIS
                WHERE id_acao = ? AND id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, idAcao);
            stmt.setLong(2, idMissao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAssociacao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar associação entre ação e missão.", e
            );
        }

        throw new EntidadeNaoEncontradaException(
                "Associação entre ação sustentável e missão não encontrada."
        );
    }

    public AcaoSustentavelMissao inserir(
            AcaoSustentavelMissao associacao) {

        validarAssociacao(associacao);

        String sql = """
                INSERT INTO T_EV_ACA_SUS_MIS (
                    id_acao, id_missao, qt_necessaria, ob_aca_sus_mis
                ) VALUES (?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, associacao.getAcaoSustentavel().getId());
            stmt.setLong(2, associacao.getMissao().getId());

            if (associacao.getQuantidadeNecessaria() == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, associacao.getQuantidadeNecessaria());
            }

            stmt.setString(4, associacao.getObrigatoria());

            stmt.executeUpdate();

            return associacao;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir associação entre ação e missão.", e
            );
        }
    }

    public void atualizar(
            long idAcao,
            long idMissao,
            AcaoSustentavelMissao associacao) {

        if (associacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da associação devem ser informados."
            );
        }

        String sql = """
                UPDATE T_EV_ACA_SUS_MIS
                SET qt_necessaria = ?,
                    ob_aca_sus_mis = ?
                WHERE id_acao = ? AND id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (associacao.getQuantidadeNecessaria() == null) {
                stmt.setNull(1, Types.INTEGER);
            } else {
                stmt.setInt(1, associacao.getQuantidadeNecessaria());
            }

            stmt.setString(2, associacao.getObrigatoria());
            stmt.setLong(3, idAcao);
            stmt.setLong(4, idMissao);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre ação sustentável e missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar associação entre ação e missão.", e
            );
        }
    }

    public void deletar(long idAcao, long idMissao) {
        String sql = """
                DELETE FROM T_EV_ACA_SUS_MIS
                WHERE id_acao = ? AND id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, idAcao);
            stmt.setLong(2, idMissao);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre ação sustentável e missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar associação entre ação e missão.", e
            );
        }
    }

    private AcaoSustentavelMissao mapearAssociacao(ResultSet rs)
            throws SQLException {

        AcaoSustentavelMissao associacao = new AcaoSustentavelMissao();

        AcaoSustentavel acao = new AcaoSustentavel();
        acao.setId(rs.getLong("id_acao"));

        Missao missao = new Missao();
        missao.setId(rs.getLong("id_missao"));

        associacao.setAcaoSustentavel(acao);
        associacao.setMissao(missao);

        int quantidade = rs.getInt("qt_necessaria");
        associacao.setQuantidadeNecessaria(
                rs.wasNull() ? null : quantidade
        );

        associacao.setObrigatoria(rs.getString("ob_aca_sus_mis"));

        return associacao;
    }

    private void validarAssociacao(AcaoSustentavelMissao associacao) {
        if (associacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da associação devem ser informados."
            );
        }

        if (associacao.getAcaoSustentavel() == null
                || associacao.getAcaoSustentavel().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da ação sustentável deve ser informado."
            );
        }

        if (associacao.getMissao() == null
                || associacao.getMissao().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da missão deve ser informado."
            );
        }
    }
}