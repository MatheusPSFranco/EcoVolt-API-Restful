package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.RecursoRevisao;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.Validacao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecursoRevisaoDao {

    private final DataSource dataSource;

    public RecursoRevisaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_recurso, id_validacao, id_usuario, id_revisor, " +
                    "mt_recurso, dc_recurso, st_recurso, dt_abertura, " +
                    "dt_resposta, rs_recurso, ob_revisor " +
                    "FROM T_EV_RECURSO_REVISAO ";

    public List<RecursoRevisao> listar() {
        List<RecursoRevisao> recursos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     SQL_BASE + "ORDER BY id_recurso");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                recursos.add(mapearRecursoRevisao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar os recursos de revisão.", e);
        }

        return recursos;
    }

    public RecursoRevisao buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_recurso = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRecursoRevisao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar o recurso de revisão.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Recurso de revisão não encontrado com o ID: " + id);
    }

    public void inserir(RecursoRevisao recurso) {
        validarRecurso(recurso);

        String sqlSequence =
                "SELECT SQ_EV_RECURSO_REVISAO.NEXTVAL FROM DUAL";

        String sqlInsert =
                "INSERT INTO T_EV_RECURSO_REVISAO " +
                        "(id_recurso, id_validacao, id_usuario, id_revisor, " +
                        "mt_recurso, dc_recurso, st_recurso, dt_abertura, " +
                        "dt_resposta, rs_recurso, ob_revisor) " +
                        "VALUES (?, ?, ?, ?, ?, ?, NVL(?, 'ABERTO'), " +
                        "NVL(?, SYSDATE), ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {

            long idGerado;

            try (PreparedStatement stmtSequence =
                         conn.prepareStatement(sqlSequence);
                 ResultSet rs = stmtSequence.executeQuery()) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID do recurso.");
                }

                idGerado = rs.getLong(1);
            }

            try (PreparedStatement stmt =
                         conn.prepareStatement(sqlInsert)) {

                stmt.setLong(1, idGerado);
                stmt.setLong(2, recurso.getValidacao().getId());

                if (recurso.getUsuario() != null
                        && recurso.getUsuario().getId() != null) {
                    stmt.setLong(3, recurso.getUsuario().getId());
                } else {
                    stmt.setNull(3, Types.NUMERIC);
                }

                stmt.setLong(4, recurso.getRevisor().getId());

                definirString(stmt, 5, recurso.getMotivo());
                definirString(stmt, 6, recurso.getDescricao());
                definirString(stmt, 7, recurso.getStatus());

                if (recurso.getDataAbertura() != null) {
                    stmt.setDate(
                            8, Date.valueOf(recurso.getDataAbertura()));
                } else {
                    stmt.setNull(8, Types.DATE);
                }

                if (recurso.getDataResposta() != null) {
                    stmt.setDate(
                            9, Date.valueOf(recurso.getDataResposta()));
                } else {
                    stmt.setNull(9, Types.DATE);
                }

                definirString(stmt, 10, recurso.getResultado());
                definirString(stmt, 11, recurso.getObservacao());

                stmt.executeUpdate();
                recurso.setId(idGerado);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao cadastrar o recurso de revisão.", e);
        }
    }

    public void atualizar(Long id, RecursoRevisao recurso) {
        validarRecurso(recurso);

        String sql =
                "UPDATE T_EV_RECURSO_REVISAO SET " +
                        "id_validacao = ?, id_usuario = ?, id_revisor = ?, " +
                        "mt_recurso = ?, dc_recurso = ?, st_recurso = ?, " +
                        "dt_abertura = NVL(?, SYSDATE), dt_resposta = ?, " +
                        "rs_recurso = ?, ob_revisor = ? " +
                        "WHERE id_recurso = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, recurso.getValidacao().getId());

            if (recurso.getUsuario() != null
                    && recurso.getUsuario().getId() != null) {
                stmt.setLong(2, recurso.getUsuario().getId());
            } else {
                stmt.setNull(2, Types.NUMERIC);
            }

            stmt.setLong(3, recurso.getRevisor().getId());

            definirString(stmt, 4, recurso.getMotivo());
            definirString(stmt, 5, recurso.getDescricao());
            definirString(stmt, 6, recurso.getStatus());

            if (recurso.getDataAbertura() != null) {
                stmt.setDate(
                        7, Date.valueOf(recurso.getDataAbertura()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            if (recurso.getDataResposta() != null) {
                stmt.setDate(
                        8, Date.valueOf(recurso.getDataResposta()));
            } else {
                stmt.setNull(8, Types.DATE);
            }

            definirString(stmt, 9, recurso.getResultado());
            definirString(stmt, 10, recurso.getObservacao());

            stmt.setLong(11, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Recurso de revisão não encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar o recurso de revisão.", e);
        }
    }

    public void deletar(Long id) {
        String sql =
                "DELETE FROM T_EV_RECURSO_REVISAO WHERE id_recurso = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Recurso de revisão não encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao excluir o recurso de revisão.", e);
        }
    }

    private RecursoRevisao mapearRecursoRevisao(ResultSet rs)
            throws SQLException {

        Validacao validacao = new Validacao();
        validacao.setId(rs.getLong("id_validacao"));

        Long idUsuario = rs.getObject("id_usuario", Long.class);
        Usuario usuario = null;

        if (idUsuario != null) {
            usuario = new Usuario();
            usuario.setId(idUsuario);
        }

        Usuario revisor = new Usuario();
        revisor.setId(rs.getLong("id_revisor"));

        Date dataAbertura = rs.getDate("dt_abertura");
        Date dataResposta = rs.getDate("dt_resposta");

        return new RecursoRevisao(
                rs.getLong("id_recurso"),
                validacao,
                usuario,
                revisor,
                rs.getString("mt_recurso"),
                rs.getString("dc_recurso"),
                rs.getString("st_recurso"),
                dataAbertura != null ? dataAbertura.toLocalDate() : null,
                dataResposta != null ? dataResposta.toLocalDate() : null,
                rs.getString("rs_recurso"),
                rs.getString("ob_revisor")
        );
    }

    private void definirString(
            PreparedStatement stmt, int indice, String valor)
            throws SQLException {

        if (valor != null) {
            stmt.setString(indice, valor);
        } else {
            stmt.setNull(indice, Types.VARCHAR);
        }
    }

    private void validarRecurso(RecursoRevisao recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException(
                    "Os dados do recurso de revisão são obrigatórios.");
        }

        if (recurso.getValidacao() == null
                || recurso.getValidacao().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID da validação.");
        }

        if (recurso.getRevisor() == null
                || recurso.getRevisor().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID do revisor.");
        }
    }
}