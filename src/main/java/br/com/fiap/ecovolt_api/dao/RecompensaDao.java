package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Recompensa;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.Tier;
import br.com.fiap.ecovolt_api.model.Ranking;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecompensaDao {

    private final DataSource dataSource;

    public RecompensaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_recompensa, id_usuario, id_tier, id_ranking, " +
                    "tp_recompensa, dc_recompensa, vl_recompensa, vl_maximo, " +
                    "st_recompensa, dt_concessao, pr_referencia " +
                    "FROM T_EV_RECOMPENSA ";

    public List<Recompensa> listar() {
        String sql = SQL_BASE + "ORDER BY id_recompensa";

        List<Recompensa> recompensas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                recompensas.add(mapearRecompensa(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar recompensas.", e);
        }

        return recompensas;
    }

    public Recompensa buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_recompensa = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRecompensa(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar recompensa.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Recompensa não encontrada com o ID: " + id
        );
    }

    public Recompensa inserir(Recompensa recompensa) {
        validarRecompensa(recompensa);

        String sql =
                "INSERT INTO T_EV_RECOMPENSA (" +
                        "id_recompensa, id_usuario, id_tier, id_ranking, " +
                        "tp_recompensa, dc_recompensa, vl_recompensa, vl_maximo, " +
                        "st_recompensa, dt_concessao, pr_referencia" +
                        ") VALUES (" +
                        "SQ_EV_RECOMPENSA.NEXTVAL, ?, ?, ?, ?, ?, " +
                        "NVL(?, 0), ?, NVL(?, 'PENDENTE'), ?, ?" +
                        ")";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql,
                     new String[]{"id_recompensa"}
             )) {

            stmt.setLong(1, recompensa.getUsuario().getId());
            stmt.setLong(2, recompensa.getTier().getId());
            stmt.setLong(3, recompensa.getRanking().getId());
            stmt.setString(4, recompensa.getTipo());
            stmt.setString(5, recompensa.getDescricao());

            if (recompensa.getValor() != null) {
                stmt.setDouble(6, recompensa.getValor());
            } else {
                stmt.setNull(6, Types.NUMERIC);
            }

            if (recompensa.getValorMaximo() != null) {
                stmt.setDouble(7, recompensa.getValorMaximo());
            } else {
                stmt.setNull(7, Types.NUMERIC);
            }

            stmt.setString(8, recompensa.getStatus());

            if (recompensa.getDataConcessao() != null) {
                stmt.setDate(
                        9,
                        Date.valueOf(recompensa.getDataConcessao())
                );
            } else {
                stmt.setNull(9, Types.DATE);
            }

            stmt.setString(10, recompensa.getPeriodoReferencia());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recompensa.setId(generatedKeys.getLong(1));
                }
            }

            if (recompensa.getId() == null) {
                throw new RuntimeException(
                        "Não foi possível recuperar o ID da recompensa inserida."
                );
            }

            return buscar(recompensa.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir recompensa.", e);
        }
    }

    public Recompensa atualizar(Long id, Recompensa recompensa) {
        validarRecompensa(recompensa);

        String sql =
                "UPDATE T_EV_RECOMPENSA SET " +
                        "id_usuario = ?, " +
                        "id_tier = ?, " +
                        "id_ranking = ?, " +
                        "tp_recompensa = ?, " +
                        "dc_recompensa = ?, " +
                        "vl_recompensa = NVL(?, 0), " +
                        "vl_maximo = ?, " +
                        "st_recompensa = NVL(?, 'PENDENTE'), " +
                        "dt_concessao = ?, " +
                        "pr_referencia = ? " +
                        "WHERE id_recompensa = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, recompensa.getUsuario().getId());
            stmt.setLong(2, recompensa.getTier().getId());
            stmt.setLong(3, recompensa.getRanking().getId());
            stmt.setString(4, recompensa.getTipo());
            stmt.setString(5, recompensa.getDescricao());

            if (recompensa.getValor() != null) {
                stmt.setDouble(6, recompensa.getValor());
            } else {
                stmt.setNull(6, Types.NUMERIC);
            }

            if (recompensa.getValorMaximo() != null) {
                stmt.setDouble(7, recompensa.getValorMaximo());
            } else {
                stmt.setNull(7, Types.NUMERIC);
            }

            stmt.setString(8, recompensa.getStatus());

            if (recompensa.getDataConcessao() != null) {
                stmt.setDate(
                        9,
                        Date.valueOf(recompensa.getDataConcessao())
                );
            } else {
                stmt.setNull(9, Types.DATE);
            }

            stmt.setString(10, recompensa.getPeriodoReferencia());
            stmt.setLong(11, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Recompensa não encontrada com o ID: " + id
                );
            }

            return buscar(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar recompensa.", e);
        }
    }

    public void deletar(Long id) {
        String sql =
                "DELETE FROM T_EV_RECOMPENSA WHERE id_recompensa = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Recompensa não encontrada com o ID: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar recompensa.", e);
        }
    }

    private Recompensa mapearRecompensa(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Tier tier = new Tier();
        tier.setId(rs.getLong("id_tier"));

        Ranking ranking = new Ranking();
        ranking.setId(rs.getLong("id_ranking"));

        Date dataConcessao = rs.getDate("dt_concessao");

        return new Recompensa(
                rs.getLong("id_recompensa"),
                usuario,
                tier,
                ranking,
                rs.getString("tp_recompensa"),
                rs.getString("dc_recompensa"),
                rs.getDouble("vl_recompensa"),
                obterDoubleNullable(rs, "vl_maximo"),
                rs.getString("st_recompensa"),
                dataConcessao != null
                        ? dataConcessao.toLocalDate()
                        : null,
                rs.getString("pr_referencia")
        );
    }

    private Double obterDoubleNullable(ResultSet rs, String coluna)
            throws SQLException {

        double valor = rs.getDouble(coluna);

        return rs.wasNull() ? null : valor;
    }

    private void validarRecompensa(Recompensa recompensa) {
        if (recompensa == null) {
            throw new IllegalArgumentException(
                    "A recompensa não pode ser nula."
            );
        }

        if (recompensa.getUsuario() == null
                || recompensa.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do usuário."
            );
        }

        if (recompensa.getTier() == null
                || recompensa.getTier().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do tier."
            );
        }

        if (recompensa.getRanking() == null
                || recompensa.getRanking().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do ranking."
            );
        }

        if (recompensa.getTipo() == null
                || recompensa.getTipo().isBlank()) {
            throw new IllegalArgumentException(
                    "O tipo da recompensa é obrigatório."
            );
        }

        if (recompensa.getPeriodoReferencia() == null
                || recompensa.getPeriodoReferencia().isBlank()) {
            throw new IllegalArgumentException(
                    "O período de referência é obrigatório."
            );
        }

        if (recompensa.getPeriodoReferencia().length() > 7) {
            throw new IllegalArgumentException(
                    "O período de referência deve ter no máximo 7 caracteres."
            );
        }
    }
}