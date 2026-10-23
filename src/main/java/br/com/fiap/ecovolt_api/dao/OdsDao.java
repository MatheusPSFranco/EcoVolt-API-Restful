package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Ods;
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
public class OdsDao {

    private final DataSource dataSource;

    public OdsDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ods> listar() {
        String sql = """
                SELECT id_ods, nr_ods, nm_ods, dc_ods, ct_ods, st_ods
                FROM T_EV_ODS
                """;

        List<Ods> lista = new ArrayList<>();

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearOds(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ODS.", e);
        }
    }

    public Ods buscar(long id) {
        String sql = """
                SELECT id_ods, nr_ods, nm_ods, dc_ods, ct_ods, st_ods
                FROM T_EV_ODS
                WHERE id_ods = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearOds(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ODS.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "ODS não encontrada."
        );
    }

    public Ods inserir(Ods ods) {
        if (ods == null) {
            throw new IllegalArgumentException(
                    "Os dados da ODS são obrigatórios."
            );
        }

        String sqlSequencia =
                "SELECT SQ_EV_ODS.NEXTVAL FROM DUAL";

        String sql = """
                INSERT INTO T_EV_ODS (
                    id_ods,
                    nr_ods,
                    nm_ods,
                    dc_ods,
                    ct_ods,
                    st_ods
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection()) {

            // Gera o ID utilizando a sequência do Oracle.
            try (Statement stmtSequencia = conexao.createStatement();
                 ResultSet rs = stmtSequencia.executeQuery(sqlSequencia)) {

                if (rs.next()) {
                    ods.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException(
                            "Não foi possível gerar o ID da ODS."
                    );
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, ods.getId());
                stmt.setInt(2, ods.getNumero());
                stmt.setString(3, ods.getNome());
                stmt.setString(4, ods.getDescricao());
                stmt.setString(5, ods.getCategoria());
                stmt.setString(6, ods.getStatus());

                stmt.executeUpdate();
            }

            return ods;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ODS.", e);
        }
    }

    public void atualizar(Ods ods) {
        if (ods == null || ods.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID e os dados da ODS são obrigatórios para atualização."
            );
        }

        String sql = """
                UPDATE T_EV_ODS
                SET nr_ods = ?,
                    nm_ods = ?,
                    dc_ods = ?,
                    ct_ods = ?,
                    st_ods = ?
                WHERE id_ods = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, ods.getNumero());
            stmt.setString(2, ods.getNome());
            stmt.setString(3, ods.getDescricao());
            stmt.setString(4, ods.getCategoria());
            stmt.setString(5, ods.getStatus());
            stmt.setLong(6, ods.getId());

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "ODS não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ODS.", e);
        }
    }

    public void deletar(long id) {
        String sql = """
                DELETE FROM T_EV_ODS
                WHERE id_ods = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "ODS não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ODS.", e);
        }
    }

    private Ods mapearOds(ResultSet rs) throws SQLException {
        return new Ods(
                rs.getLong("id_ods"),
                rs.getInt("nr_ods"),
                rs.getString("nm_ods"),
                rs.getString("dc_ods"),
                rs.getString("ct_ods"),
                rs.getString("st_ods")
        );
    }
}