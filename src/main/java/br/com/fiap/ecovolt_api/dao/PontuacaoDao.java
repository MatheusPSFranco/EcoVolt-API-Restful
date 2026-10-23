package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.*;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PontuacaoDao {

    private final DataSource dataSource;

    public PontuacaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_pontuacao, id_usuario, id_envio, id_validacao, " +
                    "id_recurso, id_missao, id_login, id_interacao, " +
                    "tp_pontuacao, pt_obtidos, xp_obtido, dt_pontuacao, " +
                    "pr_referencia, og_pontuacao, id_nivel " +
                    "FROM T_EV_PONTUACAO ";

    public List<Pontuacao> listar() {
        String sql = SQL_BASE + "ORDER BY id_pontuacao";

        List<Pontuacao> pontuacoes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pontuacoes.add(mapearPontuacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pontuações.", e);
        }

        return pontuacoes;
    }

    public Pontuacao buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_pontuacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPontuacao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pontuação.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Pontuação não encontrada com o ID: " + id
        );
    }

    public Pontuacao inserir(Pontuacao pontuacao) {
        validarPontuacao(pontuacao);

        String sql =
                "INSERT INTO T_EV_PONTUACAO (" +
                        "id_pontuacao, id_usuario, id_envio, id_validacao, " +
                        "id_recurso, id_missao, id_login, id_interacao, " +
                        "tp_pontuacao, pt_obtidos, xp_obtido, dt_pontuacao, " +
                        "pr_referencia, og_pontuacao, id_nivel" +
                        ") VALUES (" +
                        "SQ_EV_PONTUACAO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "NVL(?, 0), NVL(?, 0), NVL(?, SYSDATE), ?, ?, ?" +
                        ")";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, new String[]{"id_pontuacao"}
             )) {

            stmt.setLong(1, pontuacao.getUsuario().getId());
            definirIdOpcional(stmt, 2, obterId(pontuacao.getEnvioAcao()));
            definirIdOpcional(stmt, 3, obterId(pontuacao.getValidacao()));
            definirIdOpcional(stmt, 4, obterId(pontuacao.getRecursoRevisao()));
            definirIdOpcional(stmt, 5, obterId(pontuacao.getMissao()));
            definirIdOpcional(stmt, 6, obterId(pontuacao.getLoginDiario()));
            definirIdOpcional(stmt, 7, obterId(pontuacao.getInteracao()));

            stmt.setString(8, pontuacao.getTipo());

            if (pontuacao.getPontosObtidos() != null) {
                stmt.setInt(9, pontuacao.getPontosObtidos());
            } else {
                stmt.setNull(9, Types.NUMERIC);
            }

            if (pontuacao.getXpObtido() != null) {
                stmt.setInt(10, pontuacao.getXpObtido());
            } else {
                stmt.setNull(10, Types.NUMERIC);
            }

            if (pontuacao.getData() != null) {
                stmt.setDate(11, Date.valueOf(pontuacao.getData()));
            } else {
                stmt.setNull(11, Types.DATE);
            }

            stmt.setString(12, pontuacao.getPeriodoReferencia());
            stmt.setString(13, pontuacao.getOrigem());

            definirIdOpcional(
                    stmt, 14, obterId(pontuacao.getNivel())
            );

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pontuacao.setId(generatedKeys.getLong(1));
                }
            }

            if (pontuacao.getId() == null) {
                throw new RuntimeException(
                        "Não foi possível recuperar o ID da pontuação inserida."
                );
            }

            return buscar(pontuacao.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pontuação.", e);
        }
    }

    public Pontuacao atualizar(Long id, Pontuacao pontuacao) {
        validarPontuacao(pontuacao);

        String sql =
                "UPDATE T_EV_PONTUACAO SET " +
                        "id_usuario = ?, " +
                        "id_envio = ?, " +
                        "id_validacao = ?, " +
                        "id_recurso = ?, " +
                        "id_missao = ?, " +
                        "id_login = ?, " +
                        "id_interacao = ?, " +
                        "tp_pontuacao = ?, " +
                        "pt_obtidos = NVL(?, 0), " +
                        "xp_obtido = NVL(?, 0), " +
                        "dt_pontuacao = NVL(?, SYSDATE), " +
                        "pr_referencia = ?, " +
                        "og_pontuacao = ?, " +
                        "id_nivel = ? " +
                        "WHERE id_pontuacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pontuacao.getUsuario().getId());
            definirIdOpcional(stmt, 2, obterId(pontuacao.getEnvioAcao()));
            definirIdOpcional(stmt, 3, obterId(pontuacao.getValidacao()));
            definirIdOpcional(stmt, 4, obterId(pontuacao.getRecursoRevisao()));
            definirIdOpcional(stmt, 5, obterId(pontuacao.getMissao()));
            definirIdOpcional(stmt, 6, obterId(pontuacao.getLoginDiario()));
            definirIdOpcional(stmt, 7, obterId(pontuacao.getInteracao()));

            stmt.setString(8, pontuacao.getTipo());

            if (pontuacao.getPontosObtidos() != null) {
                stmt.setInt(9, pontuacao.getPontosObtidos());
            } else {
                stmt.setNull(9, Types.NUMERIC);
            }

            if (pontuacao.getXpObtido() != null) {
                stmt.setInt(10, pontuacao.getXpObtido());
            } else {
                stmt.setNull(10, Types.NUMERIC);
            }

            if (pontuacao.getData() != null) {
                stmt.setDate(11, Date.valueOf(pontuacao.getData()));
            } else {
                stmt.setNull(11, Types.DATE);
            }

            stmt.setString(12, pontuacao.getPeriodoReferencia());
            stmt.setString(13, pontuacao.getOrigem());

            definirIdOpcional(
                    stmt, 14, obterId(pontuacao.getNivel())
            );

            stmt.setLong(15, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Pontuação não encontrada com o ID: " + id
                );
            }

            return buscar(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pontuação.", e);
        }
    }

    public void deletar(Long id) {
        String sql =
                "DELETE FROM T_EV_PONTUACAO WHERE id_pontuacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Pontuação não encontrada com o ID: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pontuação.", e);
        }
    }

    private Pontuacao mapearPontuacao(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        EnvioAcao envio = null;
        Long idEnvio = obterIdNullable(rs, "id_envio");
        if (idEnvio != null) {
            envio = new EnvioAcao();
            envio.setId(idEnvio);
        }

        Validacao validacao = null;
        Long idValidacao = obterIdNullable(rs, "id_validacao");
        if (idValidacao != null) {
            validacao = new Validacao();
            validacao.setId(idValidacao);
        }

        RecursoRevisao recurso = null;
        Long idRecurso = obterIdNullable(rs, "id_recurso");
        if (idRecurso != null) {
            recurso = new RecursoRevisao();
            recurso.setId(idRecurso);
        }

        Missao missao = null;
        Long idMissao = obterIdNullable(rs, "id_missao");
        if (idMissao != null) {
            missao = new Missao();
            missao.setId(idMissao);
        }

        LoginDiario login = null;
        Long idLogin = obterIdNullable(rs, "id_login");
        if (idLogin != null) {
            login = new LoginDiario();
            login.setId(idLogin);
        }

        Interacao interacao = null;
        Long idInteracao = obterIdNullable(rs, "id_interacao");
        if (idInteracao != null) {
            interacao = new Interacao();
            interacao.setId(idInteracao);
        }

        Nivel nivel = null;
        Long idNivel = obterIdNullable(rs, "id_nivel");
        if (idNivel != null) {
            nivel = new Nivel();
            nivel.setId(idNivel);
        }

        Date data = rs.getDate("dt_pontuacao");

        return new Pontuacao(
                rs.getLong("id_pontuacao"),
                usuario,
                envio,
                validacao,
                recurso,
                missao,
                login,
                interacao,
                rs.getString("tp_pontuacao"),
                rs.getInt("pt_obtidos"),
                rs.getInt("xp_obtido"),
                data != null ? data.toLocalDate() : null,
                rs.getString("pr_referencia"),
                rs.getString("og_pontuacao"),
                nivel
        );
    }

    private Long obterId(Usuario objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(EnvioAcao objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(Validacao objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(RecursoRevisao objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(Missao objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(LoginDiario objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(Interacao objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private Long obterId(Nivel objeto) {
        return objeto == null ? null : objeto.getId();
    }

    private void definirIdOpcional(
            PreparedStatement stmt, int indice, Long id) throws SQLException {

        if (id != null) {
            stmt.setLong(indice, id);
        } else {
            stmt.setNull(indice, Types.NUMERIC);
        }
    }

    private Long obterIdNullable(ResultSet rs, String coluna)
            throws SQLException {

        long id = rs.getLong(coluna);
        return rs.wasNull() ? null : id;
    }

    private void validarPontuacao(Pontuacao pontuacao) {
        if (pontuacao == null) {
            throw new IllegalArgumentException(
                    "A pontuação não pode ser nula."
            );
        }

        if (pontuacao.getUsuario() == null
                || pontuacao.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do usuário."
            );
        }

        if (pontuacao.getTipo() == null
                || pontuacao.getTipo().isBlank()) {
            throw new IllegalArgumentException(
                    "O tipo da pontuação é obrigatório."
            );
        }

        if (pontuacao.getPeriodoReferencia() != null
                && pontuacao.getPeriodoReferencia().length() > 7) {
            throw new IllegalArgumentException(
                    "O período de referência deve ter no máximo 7 caracteres."
            );
        }
    }
}