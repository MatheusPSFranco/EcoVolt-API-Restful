package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.LoginDiario;
import br.com.fiap.ecovolt_api.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LoginDiarioDao {

    private final DataSource dataSource;

    public LoginDiarioDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_login, id_usuario, dt_login, tp_uso_minutos, " +
                    "lg_valido, sq_atual, bn_gerado, nt_exibida " +
                    "FROM T_EV_LOGIN_DIARIO ";

    public List<LoginDiario> listar() {
        List<LoginDiario> logins = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     SQL_BASE + "ORDER BY id_login");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                logins.add(mapearLoginDiario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar os logins diários.", e);
        }

        return logins;
    }

    public LoginDiario buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_login = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLoginDiario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar o login diário.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Login diário não encontrado com o ID: " + id);
    }

    public void inserir(LoginDiario loginDiario) {
        validarLoginDiario(loginDiario);

        String sqlSequence =
                "SELECT SQ_EV_LOGIN_DIARIO.NEXTVAL FROM DUAL";

        String sqlInsert =
                "INSERT INTO T_EV_LOGIN_DIARIO " +
                        "(id_login, id_usuario, dt_login, tp_uso_minutos, " +
                        "lg_valido, sq_atual, bn_gerado, nt_exibida) " +
                        "VALUES (?, ?, ?, NVL(?, 0), NVL(?, 'N'), " +
                        "NVL(?, 0), NVL(?, 0), NVL(?, 'N'))";

        try (Connection conn = dataSource.getConnection()) {

            long idGerado;

            try (PreparedStatement stmtSequence =
                         conn.prepareStatement(sqlSequence);
                 ResultSet rs = stmtSequence.executeQuery()) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID do login diário.");
                }

                idGerado = rs.getLong(1);
            }

            try (PreparedStatement stmt =
                         conn.prepareStatement(sqlInsert)) {

                stmt.setLong(1, idGerado);
                stmt.setLong(2, loginDiario.getUsuario().getId());
                stmt.setTimestamp(
                        3, Timestamp.valueOf(loginDiario.getData()));

                if (loginDiario.getTempoUsoMinutos() != null) {
                    stmt.setInt(4, loginDiario.getTempoUsoMinutos());
                } else {
                    stmt.setNull(4, Types.INTEGER);
                }

                if (loginDiario.getLoginValido() != null) {
                    stmt.setString(
                            5, loginDiario.getLoginValido().toString());
                } else {
                    stmt.setNull(5, Types.CHAR);
                }

                if (loginDiario.getSequenciaAtual() != null) {
                    stmt.setInt(6, loginDiario.getSequenciaAtual());
                } else {
                    stmt.setNull(6, Types.INTEGER);
                }

                if (loginDiario.getBonusGerado() != null) {
                    stmt.setInt(7, loginDiario.getBonusGerado());
                } else {
                    stmt.setNull(7, Types.INTEGER);
                }

                if (loginDiario.getNotificacaoExibida() != null) {
                    stmt.setString(
                            8, loginDiario.getNotificacaoExibida().toString());
                } else {
                    stmt.setNull(8, Types.CHAR);
                }

                stmt.executeUpdate();
                loginDiario.setId(idGerado);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o login diário.", e);
        }
    }

    public void atualizar(Long id, LoginDiario loginDiario) {
        validarLoginDiario(loginDiario);

        String sql =
                "UPDATE T_EV_LOGIN_DIARIO SET " +
                        "id_usuario = ?, dt_login = ?, tp_uso_minutos = NVL(?, 0), " +
                        "lg_valido = NVL(?, 'N'), sq_atual = NVL(?, 0), " +
                        "bn_gerado = NVL(?, 0), nt_exibida = NVL(?, 'N') " +
                        "WHERE id_login = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, loginDiario.getUsuario().getId());
            stmt.setTimestamp(
                    2, Timestamp.valueOf(loginDiario.getData()));

            if (loginDiario.getTempoUsoMinutos() != null) {
                stmt.setInt(3, loginDiario.getTempoUsoMinutos());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            if (loginDiario.getLoginValido() != null) {
                stmt.setString(
                        4, loginDiario.getLoginValido().toString());
            } else {
                stmt.setNull(4, Types.CHAR);
            }

            if (loginDiario.getSequenciaAtual() != null) {
                stmt.setInt(5, loginDiario.getSequenciaAtual());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            if (loginDiario.getBonusGerado() != null) {
                stmt.setInt(6, loginDiario.getBonusGerado());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (loginDiario.getNotificacaoExibida() != null) {
                stmt.setString(
                        7, loginDiario.getNotificacaoExibida().toString());
            } else {
                stmt.setNull(7, Types.CHAR);
            }

            stmt.setLong(8, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Login diário não encontrado com o ID: " + id);
            }

            loginDiario.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o login diário.", e);
        }
    }

    public void deletar(Long id) {
        String sql =
                "DELETE FROM T_EV_LOGIN_DIARIO WHERE id_login = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Login diário não encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o login diário.", e);
        }
    }

    private LoginDiario mapearLoginDiario(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Timestamp timestamp = rs.getTimestamp("dt_login");

        return new LoginDiario(
                rs.getLong("id_login"),
                usuario,
                timestamp != null ? timestamp.toLocalDateTime() : null,
                rs.getInt("tp_uso_minutos"),
                converterCharacter(rs.getString("lg_valido")),
                rs.getInt("sq_atual"),
                rs.getInt("bn_gerado"),
                converterCharacter(rs.getString("nt_exibida"))
        );
    }

    private Character converterCharacter(String valor) {
        return valor != null && !valor.isEmpty()
                ? valor.charAt(0)
                : null;
    }

    private void validarLoginDiario(LoginDiario loginDiario) {
        if (loginDiario == null) {
            throw new IllegalArgumentException(
                    "Os dados do login diário são obrigatórios.");
        }

        if (loginDiario.getUsuario() == null
                || loginDiario.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID do usuário.");
        }

        if (loginDiario.getData() == null) {
            throw new IllegalArgumentException(
                    "A data do login é obrigatória.");
        }
    }
}