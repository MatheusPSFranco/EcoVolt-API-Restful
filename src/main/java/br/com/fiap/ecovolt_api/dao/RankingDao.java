package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Ranking;
import br.com.fiap.ecovolt_api.model.Tier;
import br.com.fiap.ecovolt_api.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RankingDao {

    private final DataSource dataSource;

    public RankingDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_ranking, id_usuario, id_tier, pr_referencia, " +
                    "pt_total_periodo, pc_ranking, st_ranking, dt_atualizacao " +
                    "FROM T_EV_RANKING ";

    public List<Ranking> listar() {
        List<Ranking> rankings = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     SQL_BASE + "ORDER BY pr_referencia DESC, pc_ranking ASC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rankings.add(mapearRanking(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar os rankings.", e);
        }

        return rankings;
    }

    public Ranking buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_ranking = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRanking(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o ranking.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Ranking não encontrado com o ID: " + id);
    }

    public void inserir(Ranking ranking) {
        validarRanking(ranking);

        String sqlSequence =
                "SELECT SQ_EV_RANKING.NEXTVAL FROM DUAL";

        String sqlInsert =
                "INSERT INTO T_EV_RANKING " +
                        "(id_ranking, id_usuario, id_tier, pr_referencia, " +
                        "pt_total_periodo, pc_ranking, st_ranking, dt_atualizacao) " +
                        "VALUES (?, ?, ?, ?, NVL(?, 0), ?, NVL(?, 'ATIVO'), " +
                        "NVL(?, SYSDATE))";

        try (Connection conn = dataSource.getConnection()) {

            long idGerado;

            try (PreparedStatement stmtSequence =
                         conn.prepareStatement(sqlSequence);
                 ResultSet rs = stmtSequence.executeQuery()) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID do ranking.");
                }

                idGerado = rs.getLong(1);
            }

            try (PreparedStatement stmt =
                         conn.prepareStatement(sqlInsert)) {

                stmt.setLong(1, idGerado);
                stmt.setLong(2, ranking.getUsuario().getId());
                stmt.setLong(3, ranking.getTier().getId());
                stmt.setString(4, ranking.getPeriodoReferencia());

                if (ranking.getPontuacaoTotalPeriodo() != null) {
                    stmt.setInt(5, ranking.getPontuacaoTotalPeriodo());
                } else {
                    stmt.setNull(5, Types.INTEGER);
                }

                stmt.setInt(6, ranking.getPosicao());

                if (ranking.getStatus() != null) {
                    stmt.setString(7, ranking.getStatus());
                } else {
                    stmt.setNull(7, Types.VARCHAR);
                }

                if (ranking.getDataAtualizacao() != null) {
                    stmt.setDate(
                            8, Date.valueOf(ranking.getDataAtualizacao()));
                } else {
                    stmt.setNull(8, Types.DATE);
                }

                stmt.executeUpdate();
                ranking.setId(idGerado);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o ranking.", e);
        }
    }

    public void atualizar(Long id, Ranking ranking) {
        validarRanking(ranking);

        String sql =
                "UPDATE T_EV_RANKING SET " +
                        "id_usuario = ?, id_tier = ?, pr_referencia = ?, " +
                        "pt_total_periodo = NVL(?, 0), pc_ranking = ?, " +
                        "st_ranking = NVL(?, 'ATIVO'), " +
                        "dt_atualizacao = NVL(?, SYSDATE) " +
                        "WHERE id_ranking = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ranking.getUsuario().getId());
            stmt.setLong(2, ranking.getTier().getId());
            stmt.setString(3, ranking.getPeriodoReferencia());

            if (ranking.getPontuacaoTotalPeriodo() != null) {
                stmt.setInt(4, ranking.getPontuacaoTotalPeriodo());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, ranking.getPosicao());

            if (ranking.getStatus() != null) {
                stmt.setString(6, ranking.getStatus());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }

            if (ranking.getDataAtualizacao() != null) {
                stmt.setDate(
                        7, Date.valueOf(ranking.getDataAtualizacao()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            stmt.setLong(8, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Ranking não encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o ranking.", e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM T_EV_RANKING WHERE id_ranking = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Ranking não encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o ranking.", e);
        }
    }

    private Ranking mapearRanking(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Tier tier = new Tier();
        tier.setId(rs.getLong("id_tier"));

        Date dataAtualizacao = rs.getDate("dt_atualizacao");

        return new Ranking(
                rs.getLong("id_ranking"),
                usuario,
                tier,
                rs.getString("pr_referencia"),
                rs.getInt("pt_total_periodo"),
                rs.getInt("pc_ranking"),
                rs.getString("st_ranking"),
                dataAtualizacao != null
                        ? dataAtualizacao.toLocalDate()
                        : null
        );
    }

    private void validarRanking(Ranking ranking) {
        if (ranking == null) {
            throw new IllegalArgumentException(
                    "Os dados do ranking são obrigatórios.");
        }

        if (ranking.getUsuario() == null
                || ranking.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID do usuário.");
        }

        if (ranking.getTier() == null
                || ranking.getTier().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID do tier.");
        }

        if (ranking.getPeriodoReferencia() == null
                || ranking.getPeriodoReferencia().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O período de referência é obrigatório.");
        }

        if (ranking.getPeriodoReferencia().length() > 7) {
            throw new IllegalArgumentException(
                    "O período de referência deve ter no máximo 7 caracteres.");
        }

        if (ranking.getPosicao() == null) {
            throw new IllegalArgumentException(
                    "A posição no ranking é obrigatória.");
        }
    }
}