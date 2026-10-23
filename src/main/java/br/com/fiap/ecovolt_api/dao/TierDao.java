package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Tier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TierDao {

    private final DataSource dataSource;

    public TierDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Tier> listar() {
        String sql = """
                SELECT id_tier, nm_tier, dc_tier, rd_tier,
                       us_ativos_minimos, pc_subida, pc_descida,
                       lm_usuarios, st_tier
                FROM T_EV_TIER
                """;

        List<Tier> lista = new ArrayList<>();

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearTier(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tiers.", e);
        }
    }

    public Tier buscar(long id) {
        String sql = """
                SELECT id_tier, nm_tier, dc_tier, rd_tier,
                       us_ativos_minimos, pc_subida, pc_descida,
                       lm_usuarios, st_tier
                FROM T_EV_TIER
                WHERE id_tier = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTier(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tier.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Tier não encontrado."
        );
    }

    public Tier inserir(Tier tier) {
        if (tier == null) {
            throw new IllegalArgumentException(
                    "Os dados do tier são obrigatórios."
            );
        }

        String sqlSequencia =
                "SELECT SQ_EV_TIER.NEXTVAL FROM DUAL";

        String sql = """
                INSERT INTO T_EV_TIER (
                    id_tier,
                    nm_tier,
                    dc_tier,
                    rd_tier,
                    us_ativos_minimos,
                    pc_subida,
                    pc_descida,
                    lm_usuarios,
                    st_tier
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection()) {

            // Gera o ID pela sequência Oracle.
            try (Statement stmtSequencia = conexao.createStatement();
                 ResultSet rs = stmtSequencia.executeQuery(sqlSequencia)) {

                if (rs.next()) {
                    tier.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException(
                            "Não foi possível gerar o ID do tier."
                    );
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, tier.getId());
                stmt.setString(2, tier.getNome());
                stmt.setString(3, tier.getDescricao());
                stmt.setInt(4, tier.getOrdem());
                stmt.setInt(5, tier.getUsuariosAtivosMinimos());
                stmt.setDouble(6, tier.getPercentualSubida());
                stmt.setDouble(7, tier.getPercentualDescida());
                stmt.setInt(8, tier.getLimiteUsuarios());
                stmt.setString(9, tier.getStatus());

                stmt.executeUpdate();
            }

            return tier;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tier.", e);
        }
    }

    public void atualizar(Tier tier) {
        if (tier == null || tier.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID e os dados do tier são obrigatórios para atualização."
            );
        }

        String sql = """
                UPDATE T_EV_TIER
                SET nm_tier = ?,
                    dc_tier = ?,
                    rd_tier = ?,
                    us_ativos_minimos = ?,
                    pc_subida = ?,
                    pc_descida = ?,
                    lm_usuarios = ?,
                    st_tier = ?
                WHERE id_tier = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, tier.getNome());
            stmt.setString(2, tier.getDescricao());
            stmt.setInt(3, tier.getOrdem());
            stmt.setInt(4, tier.getUsuariosAtivosMinimos());
            stmt.setDouble(5, tier.getPercentualSubida());
            stmt.setDouble(6, tier.getPercentualDescida());
            stmt.setInt(7, tier.getLimiteUsuarios());
            stmt.setString(8, tier.getStatus());
            stmt.setLong(9, tier.getId());

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Tier não encontrado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tier.", e);
        }
    }

    public void deletar(long id) {
        String sql = """
                DELETE FROM T_EV_TIER
                WHERE id_tier = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Tier não encontrado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tier.", e);
        }
    }

    private Tier mapearTier(ResultSet rs) throws SQLException {
        return new Tier(
                rs.getLong("id_tier"),
                rs.getString("nm_tier"),
                rs.getString("dc_tier"),
                rs.getInt("rd_tier"),
                rs.getInt("us_ativos_minimos"),
                rs.getDouble("pc_subida"),
                rs.getDouble("pc_descida"),
                rs.getInt("lm_usuarios"),
                rs.getString("st_tier")
        );
    }
}