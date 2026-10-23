package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Nivel;
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
public class NivelDao {

    private final DataSource dataSource;

    public NivelDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Nivel> listar() {
        String sql = """
                SELECT id_nivel, nr_nivel, xp_minimo, xp_maximo,
                       dc_nivel, bd_perfil, st_nivel
                FROM T_EV_NIVEL
                """;

        List<Nivel> niveis = new ArrayList<>();

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                niveis.add(mapearNivel(rs));
            }

            return niveis;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar níveis.", e);
        }
    }

    public Nivel buscar(long id) {
        String sql = """
                SELECT id_nivel, nr_nivel, xp_minimo, xp_maximo,
                       dc_nivel, bd_perfil, st_nivel
                FROM T_EV_NIVEL
                WHERE id_nivel = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearNivel(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nível.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Nível não encontrado."
        );
    }

    public Nivel inserir(Nivel nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException(
                    "Os dados do nível são obrigatórios."
            );
        }

        String sqlSequencia =
                "SELECT SQ_EV_NIVEL.NEXTVAL FROM DUAL";

        String sql = """
                INSERT INTO T_EV_NIVEL (
                    id_nivel,
                    nr_nivel,
                    xp_minimo,
                    xp_maximo,
                    dc_nivel,
                    bd_perfil,
                    st_nivel
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection()) {

            // Obtém o ID pela sequência do Oracle.
            try (Statement stmtSequencia = conexao.createStatement();
                 ResultSet rs = stmtSequencia.executeQuery(sqlSequencia)) {

                if (rs.next()) {
                    nivel.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException(
                            "Não foi possível gerar o ID do nível."
                    );
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, nivel.getId());
                stmt.setInt(2, nivel.getNumero());
                stmt.setInt(3, nivel.getXpMinimo());
                stmt.setInt(4, nivel.getXpMaximo());
                stmt.setString(5, nivel.getDescricao());
                stmt.setString(6, nivel.getBordaPerfil());
                stmt.setString(7, nivel.getStatus());

                stmt.executeUpdate();
            }

            return nivel;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir nível.", e);
        }
    }

    public void atualizar(Nivel nivel) {
        if (nivel == null || nivel.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID e os dados do nível são obrigatórios para atualização."
            );
        }

        String sql = """
                UPDATE T_EV_NIVEL
                SET nr_nivel = ?,
                    xp_minimo = ?,
                    xp_maximo = ?,
                    dc_nivel = ?,
                    bd_perfil = ?,
                    st_nivel = ?
                WHERE id_nivel = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, nivel.getNumero());
            stmt.setInt(2, nivel.getXpMinimo());
            stmt.setInt(3, nivel.getXpMaximo());
            stmt.setString(4, nivel.getDescricao());
            stmt.setString(5, nivel.getBordaPerfil());
            stmt.setString(6, nivel.getStatus());
            stmt.setLong(7, nivel.getId());

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Nível não encontrado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nível.", e);
        }
    }

    public void deletar(long id) {
        String sql = """
                DELETE FROM T_EV_NIVEL
                WHERE id_nivel = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Nível não encontrado."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar nível.", e);
        }
    }

    private Nivel mapearNivel(ResultSet rs) throws SQLException {
        return new Nivel(
                rs.getLong("id_nivel"),
                rs.getInt("nr_nivel"),
                rs.getInt("xp_minimo"),
                rs.getInt("xp_maximo"),
                rs.getString("dc_nivel"),
                rs.getString("bd_perfil"),
                rs.getString("st_nivel")
        );
    }
}