package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Conquista;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.UsuarioConquista;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioConquistaDao {

    private final DataSource dataSource;

    public UsuarioConquistaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_usuario, id_conquista, dt_desbloqueio, " +
                    "pg_atual, st_conquista_usuario, vs_perfil " +
                    "FROM T_EV_USU_CON ";

    public List<UsuarioConquista> listar() {
        List<UsuarioConquista> associacoes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     SQL_BASE + "ORDER BY id_usuario, id_conquista");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                associacoes.add(mapearUsuarioConquista(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar as conquistas dos usuários.", e);
        }

        return associacoes;
    }

    public UsuarioConquista buscar(Long idUsuario, Long idConquista) {
        String sql = SQL_BASE +
                "WHERE id_usuario = ? AND id_conquista = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idUsuario);
            stmt.setLong(2, idConquista);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuarioConquista(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar a conquista do usuário.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Associação entre o usuário " + idUsuario +
                        " e a conquista " + idConquista + " não encontrada.");
    }

    public void inserir(UsuarioConquista usuarioConquista) {
        validarUsuarioConquista(usuarioConquista);

        String sql =
                "INSERT INTO T_EV_USU_CON " +
                        "(id_usuario, id_conquista, dt_desbloqueio, pg_atual, " +
                        "st_conquista_usuario, vs_perfil) " +
                        "VALUES (?, ?, ?, NVL(?, 0), " +
                        "NVL(?, 'EM_ANDAMENTO'), NVL(?, 'S'))";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioConquista.getUsuario().getId());
            stmt.setLong(2, usuarioConquista.getConquista().getId());

            if (usuarioConquista.getDataDesbloqueio() != null) {
                stmt.setTimestamp(
                        3, Timestamp.valueOf(
                                usuarioConquista.getDataDesbloqueio()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            if (usuarioConquista.getProgressoAtual() != null) {
                stmt.setInt(4, usuarioConquista.getProgressoAtual());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            if (usuarioConquista.getStatus() != null) {
                stmt.setString(5, usuarioConquista.getStatus());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            if (usuarioConquista.getVisivelPerfil() != null) {
                stmt.setString(
                        6, usuarioConquista.getVisivelPerfil().toString());
            } else {
                stmt.setNull(6, Types.CHAR);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao cadastrar a conquista do usuário.", e);
        }
    }

    public void atualizar(
            Long idUsuario,
            Long idConquista,
            UsuarioConquista usuarioConquista) {

        validarUsuarioConquista(usuarioConquista);

        String sql =
                "UPDATE T_EV_USU_CON SET " +
                        "dt_desbloqueio = ?, " +
                        "pg_atual = NVL(?, 0), " +
                        "st_conquista_usuario = NVL(?, 'EM_ANDAMENTO'), " +
                        "vs_perfil = NVL(?, 'S') " +
                        "WHERE id_usuario = ? AND id_conquista = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (usuarioConquista.getDataDesbloqueio() != null) {
                stmt.setTimestamp(
                        1, Timestamp.valueOf(
                                usuarioConquista.getDataDesbloqueio()));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }

            if (usuarioConquista.getProgressoAtual() != null) {
                stmt.setInt(2, usuarioConquista.getProgressoAtual());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            if (usuarioConquista.getStatus() != null) {
                stmt.setString(3, usuarioConquista.getStatus());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }

            if (usuarioConquista.getVisivelPerfil() != null) {
                stmt.setString(
                        4, usuarioConquista.getVisivelPerfil().toString());
            } else {
                stmt.setNull(4, Types.CHAR);
            }

            stmt.setLong(5, idUsuario);
            stmt.setLong(6, idConquista);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre o usuário " + idUsuario +
                                " e a conquista " + idConquista + " não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar a conquista do usuário.", e);
        }
    }

    public void deletar(Long idUsuario, Long idConquista) {
        String sql =
                "DELETE FROM T_EV_USU_CON " +
                        "WHERE id_usuario = ? AND id_conquista = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idUsuario);
            stmt.setLong(2, idConquista);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre o usuário " + idUsuario +
                                " e a conquista " + idConquista + " não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao excluir a conquista do usuário.", e);
        }
    }

    private UsuarioConquista mapearUsuarioConquista(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Conquista conquista = new Conquista();
        conquista.setId(rs.getLong("id_conquista"));

        Timestamp dataDesbloqueio = rs.getTimestamp("dt_desbloqueio");

        return new UsuarioConquista(
                usuario,
                conquista,
                dataDesbloqueio != null
                        ? dataDesbloqueio.toLocalDateTime()
                        : null,
                rs.getInt("pg_atual"),
                rs.getString("st_conquista_usuario"),
                converterCharacter(rs.getString("vs_perfil"))
        );
    }

    private Character converterCharacter(String valor) {
        return valor != null && !valor.isEmpty()
                ? valor.charAt(0)
                : null;
    }

    private void validarUsuarioConquista(
            UsuarioConquista usuarioConquista) {

        if (usuarioConquista == null) {
            throw new IllegalArgumentException(
                    "Os dados da conquista do usuário são obrigatórios.");
        }

        if (usuarioConquista.getUsuario() == null
                || usuarioConquista.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID do usuário.");
        }

        if (usuarioConquista.getConquista() == null
                || usuarioConquista.getConquista().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID da conquista.");
        }
    }
}