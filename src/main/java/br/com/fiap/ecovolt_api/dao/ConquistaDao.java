package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Conquista;
import br.com.fiap.ecovolt_api.model.Nivel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConquistaDao {

    private final DataSource dataSource;

    public ConquistaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Conquista> listar() {
        List<Conquista> conquistas = new ArrayList<>();

        String sql = """
                SELECT id_conquista, id_nivel, nm_conquista,
                       dc_conquista, tp_conquista, cr_desbloqueio,
                       qt_necessaria, md_conquista, st_conquista
                FROM T_EV_CONQUISTA
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                conquistas.add(mapearConquista(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar conquistas.", e
            );
        }

        return conquistas;
    }

    public Conquista buscar(long id) {
        String sql = """
                SELECT id_conquista, id_nivel, nm_conquista,
                       dc_conquista, tp_conquista, cr_desbloqueio,
                       qt_necessaria, md_conquista, st_conquista
                FROM T_EV_CONQUISTA
                WHERE id_conquista = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearConquista(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar conquista.", e
            );
        }

        throw new EntidadeNaoEncontradaException(
                "Conquista não encontrada."
        );
    }

    public Conquista inserir(Conquista conquista) {
        validarConquista(conquista);

        String sqlSequencia =
                "SELECT SQ_EV_CONQUISTA.NEXTVAL FROM DUAL";

        String sql = """
                INSERT INTO T_EV_CONQUISTA (
                    id_conquista, id_nivel, nm_conquista,
                    dc_conquista, tp_conquista, cr_desbloqueio,
                    qt_necessaria, md_conquista, st_conquista
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection()) {

            try (Statement stmtSequencia = conexao.createStatement();
                 ResultSet rs = stmtSequencia.executeQuery(sqlSequencia)) {

                if (rs.next()) {
                    conquista.setId(rs.getLong(1));
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, conquista.getId());

                definirNivel(stmt, 2, conquista.getNivel());

                stmt.setString(3, conquista.getNome());
                stmt.setString(4, conquista.getDescricao());
                stmt.setString(5, conquista.getTipo());
                stmt.setString(6, conquista.getCriterioDesbloqueio());

                if (conquista.getQuantidadeNecessaria() == null) {
                    stmt.setNull(7, Types.NUMERIC);
                } else {
                    stmt.setInt(
                            7, conquista.getQuantidadeNecessaria()
                    );
                }

                stmt.setString(8, conquista.getMedalha());
                stmt.setString(9, conquista.getStatus());

                stmt.executeUpdate();
            }

            return conquista;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir conquista.", e
            );
        }
    }

    public void atualizar(Conquista conquista) {
        validarConquista(conquista);

        String sql = """
                UPDATE T_EV_CONQUISTA
                SET id_nivel = ?,
                    nm_conquista = ?,
                    dc_conquista = ?,
                    tp_conquista = ?,
                    cr_desbloqueio = ?,
                    qt_necessaria = ?,
                    md_conquista = ?,
                    st_conquista = ?
                WHERE id_conquista = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            definirNivel(stmt, 1, conquista.getNivel());

            stmt.setString(2, conquista.getNome());
            stmt.setString(3, conquista.getDescricao());
            stmt.setString(4, conquista.getTipo());
            stmt.setString(5, conquista.getCriterioDesbloqueio());

            if (conquista.getQuantidadeNecessaria() == null) {
                stmt.setNull(6, Types.NUMERIC);
            } else {
                stmt.setInt(
                        6, conquista.getQuantidadeNecessaria()
                );
            }

            stmt.setString(7, conquista.getMedalha());
            stmt.setString(8, conquista.getStatus());
            stmt.setLong(9, conquista.getId());

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Conquista não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar conquista.", e
            );
        }
    }

    public void deletar(long id) {
        String sql =
                "DELETE FROM T_EV_CONQUISTA WHERE id_conquista = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Conquista não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar conquista.", e
            );
        }
    }

    private Conquista mapearConquista(ResultSet rs)
            throws SQLException {

        Conquista conquista = new Conquista();

        conquista.setId(rs.getLong("id_conquista"));
        conquista.setNome(rs.getString("nm_conquista"));
        conquista.setDescricao(rs.getString("dc_conquista"));
        conquista.setTipo(rs.getString("tp_conquista"));
        conquista.setCriterioDesbloqueio(
                rs.getString("cr_desbloqueio")
        );

        int quantidade = rs.getInt("qt_necessaria");

        conquista.setQuantidadeNecessaria(
                rs.wasNull() ? null : quantidade
        );

        conquista.setMedalha(rs.getString("md_conquista"));
        conquista.setStatus(rs.getString("st_conquista"));

        long idNivel = rs.getLong("id_nivel");

        if (!rs.wasNull()) {
            Nivel nivel = new Nivel();
            nivel.setId(idNivel);
            conquista.setNivel(nivel);
        }

        return conquista;
    }

    private void definirNivel(
            PreparedStatement stmt,
            int indice,
            Nivel nivel) throws SQLException {

        if (nivel != null && nivel.getId() != null) {
            stmt.setLong(indice, nivel.getId());
        } else {
            stmt.setNull(indice, Types.NUMERIC);
        }
    }

    private void validarConquista(Conquista conquista) {
        if (conquista == null) {
            throw new IllegalArgumentException(
                    "Os dados da conquista devem ser informados."
            );
        }
    }
}