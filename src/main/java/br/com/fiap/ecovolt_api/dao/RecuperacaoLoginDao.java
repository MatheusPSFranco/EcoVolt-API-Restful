package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.RecuperacaoLogin;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.LoginDiario;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecuperacaoLoginDao {

    private final DataSource dataSource;

    public RecuperacaoLoginDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_recuperacao, id_usuario, id_login, " +
                    "dt_recuperacao, dt_login_recuperado, ms_referencia, " +
                    "an_assistido, ano_referencia, st_recuperacao " +
                    "FROM T_EV_RECUPERACAO_LOGIN ";

    public List<RecuperacaoLogin> listar() {
        String sql = SQL_BASE + "ORDER BY id_recuperacao";

        List<RecuperacaoLogin> recuperacoes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                recuperacoes.add(mapearRecuperacaoLogin(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar recuperações de login.", e
            );
        }

        return recuperacoes;
    }

    public RecuperacaoLogin buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_recuperacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRecuperacaoLogin(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar recuperação de login.", e
            );
        }

        throw new EntidadeNaoEncontradaException(
                "Recuperação de login não encontrada com o ID: " + id
        );
    }

    public RecuperacaoLogin inserir(RecuperacaoLogin recuperacao) {
        validarRecuperacao(recuperacao);

        String sql =
                "INSERT INTO T_EV_RECUPERACAO_LOGIN (" +
                        "id_recuperacao, id_usuario, id_login, dt_recuperacao, " +
                        "dt_login_recuperado, ms_referencia, an_assistido, " +
                        "ano_referencia, st_recuperacao" +
                        ") VALUES (" +
                        "SQ_EV_RECUPERACAO_LOGIN.NEXTVAL, ?, ?, " +
                        "NVL(?, SYSDATE), ?, ?, NVL(?, 'S'), ?, " +
                        "NVL(?, 'APROVADA')" +
                        ")";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, new String[]{"id_recuperacao"}
             )) {

            stmt.setLong(1, recuperacao.getUsuario().getId());
            stmt.setLong(2, recuperacao.getLogin().getId());

            if (recuperacao.getDataRecuperacao() != null) {
                stmt.setTimestamp(
                        3,
                        Timestamp.valueOf(recuperacao.getDataRecuperacao())
                );
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            stmt.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            recuperacao.getDataLoginRecuperado()
                    )
            );

            stmt.setString(5, recuperacao.getMesReferencia());

            if (recuperacao.getAnuncioAssistido() != null) {
                stmt.setString(
                        6,
                        recuperacao.getAnuncioAssistido().toString()
                );
            } else {
                stmt.setNull(6, Types.CHAR);
            }

            stmt.setInt(7, recuperacao.getAnoReferencia());
            stmt.setString(8, recuperacao.getStatus());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recuperacao.setId(generatedKeys.getLong(1));
                }
            }

            if (recuperacao.getId() == null) {
                throw new RuntimeException(
                        "Não foi possível recuperar o ID da recuperação inserida."
                );
            }

            return buscar(recuperacao.getId());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir recuperação de login.", e
            );
        }
    }

    public RecuperacaoLogin atualizar(
            Long id,
            RecuperacaoLogin recuperacao) {

        validarRecuperacao(recuperacao);

        String sql =
                "UPDATE T_EV_RECUPERACAO_LOGIN SET " +
                        "id_usuario = ?, " +
                        "id_login = ?, " +
                        "dt_recuperacao = NVL(?, SYSDATE), " +
                        "dt_login_recuperado = ?, " +
                        "ms_referencia = ?, " +
                        "an_assistido = NVL(?, 'S'), " +
                        "ano_referencia = ?, " +
                        "st_recuperacao = NVL(?, 'APROVADA') " +
                        "WHERE id_recuperacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, recuperacao.getUsuario().getId());
            stmt.setLong(2, recuperacao.getLogin().getId());

            if (recuperacao.getDataRecuperacao() != null) {
                stmt.setTimestamp(
                        3,
                        Timestamp.valueOf(recuperacao.getDataRecuperacao())
                );
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            stmt.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            recuperacao.getDataLoginRecuperado()
                    )
            );

            stmt.setString(5, recuperacao.getMesReferencia());

            if (recuperacao.getAnuncioAssistido() != null) {
                stmt.setString(
                        6,
                        recuperacao.getAnuncioAssistido().toString()
                );
            } else {
                stmt.setNull(6, Types.CHAR);
            }

            stmt.setInt(7, recuperacao.getAnoReferencia());
            stmt.setString(8, recuperacao.getStatus());
            stmt.setLong(9, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Recuperação de login não encontrada com o ID: " + id
                );
            }

            return buscar(id);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar recuperação de login.", e
            );
        }
    }

    public void deletar(Long id) {
        String sql =
                "DELETE FROM T_EV_RECUPERACAO_LOGIN " +
                        "WHERE id_recuperacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Recuperação de login não encontrada com o ID: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar recuperação de login.", e
            );
        }
    }

    private RecuperacaoLogin mapearRecuperacaoLogin(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        LoginDiario login = new LoginDiario();
        login.setId(rs.getLong("id_login"));

        Timestamp dataRecuperacao =
                rs.getTimestamp("dt_recuperacao");

        Timestamp dataLoginRecuperado =
                rs.getTimestamp("dt_login_recuperado");

        String anuncio = rs.getString("an_assistido");

        return new RecuperacaoLogin(
                rs.getLong("id_recuperacao"),
                usuario,
                login,
                dataRecuperacao != null
                        ? dataRecuperacao.toLocalDateTime()
                        : null,
                dataLoginRecuperado != null
                        ? dataLoginRecuperado.toLocalDateTime()
                        : null,
                rs.getString("ms_referencia"),
                anuncio != null && !anuncio.isEmpty()
                        ? anuncio.charAt(0)
                        : null,
                rs.getInt("ano_referencia"),
                rs.getString("st_recuperacao")
        );
    }

    private void validarRecuperacao(RecuperacaoLogin recuperacao) {
        if (recuperacao == null) {
            throw new IllegalArgumentException(
                    "A recuperação de login não pode ser nula."
            );
        }

        if (recuperacao.getUsuario() == null
                || recuperacao.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do usuário."
            );
        }

        if (recuperacao.getLogin() == null
                || recuperacao.getLogin().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do login diário."
            );
        }

        if (recuperacao.getDataLoginRecuperado() == null) {
            throw new IllegalArgumentException(
                    "A data do login recuperado é obrigatória."
            );
        }

        if (recuperacao.getMesReferencia() == null
                || recuperacao.getMesReferencia().isBlank()) {
            throw new IllegalArgumentException(
                    "O mês de referência é obrigatório."
            );
        }

        if (recuperacao.getMesReferencia().length() > 7) {
            throw new IllegalArgumentException(
                    "O mês de referência deve ter no máximo 7 caracteres."
            );
        }

        if (recuperacao.getAnoReferencia() == null) {
            throw new IllegalArgumentException(
                    "O ano de referência é obrigatório."
            );
        }

        Character anuncio = recuperacao.getAnuncioAssistido();

        if (anuncio != null
                && anuncio != 'S'
                && anuncio != 'N') {
            throw new IllegalArgumentException(
                    "O anúncio assistido deve ser 'S' ou 'N'."
            );
        }
    }
}