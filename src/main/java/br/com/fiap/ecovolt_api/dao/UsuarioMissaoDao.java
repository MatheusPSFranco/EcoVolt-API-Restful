package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Missao;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.UsuarioMissao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioMissaoDao {

    private final DataSource dataSource;

    public UsuarioMissaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_usuario, id_missao, dt_inicio, dt_conclusao, " +
                    "st_missao_usuario, pg_missao_usuario " +
                    "FROM T_EV_USU_MIS ";

    public List<UsuarioMissao> listar() {
        List<UsuarioMissao> associacoes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     SQL_BASE + "ORDER BY id_usuario, id_missao");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                associacoes.add(mapearUsuarioMissao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar as missões dos usuários.", e);
        }

        return associacoes;
    }

    public UsuarioMissao buscar(Long idUsuario, Long idMissao) {
        String sql = SQL_BASE +
                "WHERE id_usuario = ? AND id_missao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idUsuario);
            stmt.setLong(2, idMissao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuarioMissao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar a missão do usuário.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Associação entre o usuário " + idUsuario +
                        " e a missão " + idMissao + " não encontrada.");
    }

    public void inserir(UsuarioMissao usuarioMissao) {
        validarUsuarioMissao(usuarioMissao);

        String sql =
                "INSERT INTO T_EV_USU_MIS " +
                        "(id_usuario, id_missao, dt_inicio, dt_conclusao, " +
                        "st_missao_usuario, pg_missao_usuario) " +
                        "VALUES (?, ?, NVL(?, SYSDATE), ?, " +
                        "NVL(?, 'EM_ANDAMENTO'), NVL(?, 0))";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioMissao.getUsuario().getId());
            stmt.setLong(2, usuarioMissao.getMissao().getId());

            if (usuarioMissao.getDataInicio() != null) {
                stmt.setDate(
                        3, Date.valueOf(usuarioMissao.getDataInicio()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            if (usuarioMissao.getDataConclusao() != null) {
                stmt.setDate(
                        4, Date.valueOf(usuarioMissao.getDataConclusao()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            if (usuarioMissao.getStatus() != null) {
                stmt.setString(5, usuarioMissao.getStatus());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            if (usuarioMissao.getProgresso() != null) {
                stmt.setDouble(6, usuarioMissao.getProgresso());
            } else {
                stmt.setNull(6, Types.NUMERIC);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao cadastrar a missão do usuário.", e);
        }
    }

    public void atualizar(
            Long idUsuario,
            Long idMissao,
            UsuarioMissao usuarioMissao) {

        validarUsuarioMissao(usuarioMissao);

        String sql =
                "UPDATE T_EV_USU_MIS SET " +
                        "dt_inicio = NVL(?, SYSDATE), " +
                        "dt_conclusao = ?, " +
                        "st_missao_usuario = NVL(?, 'EM_ANDAMENTO'), " +
                        "pg_missao_usuario = NVL(?, 0) " +
                        "WHERE id_usuario = ? AND id_missao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (usuarioMissao.getDataInicio() != null) {
                stmt.setDate(
                        1, Date.valueOf(usuarioMissao.getDataInicio()));
            } else {
                stmt.setNull(1, Types.DATE);
            }

            if (usuarioMissao.getDataConclusao() != null) {
                stmt.setDate(
                        2, Date.valueOf(usuarioMissao.getDataConclusao()));
            } else {
                stmt.setNull(2, Types.DATE);
            }

            if (usuarioMissao.getStatus() != null) {
                stmt.setString(3, usuarioMissao.getStatus());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }

            if (usuarioMissao.getProgresso() != null) {
                stmt.setDouble(4, usuarioMissao.getProgresso());
            } else {
                stmt.setNull(4, Types.NUMERIC);
            }

            stmt.setLong(5, idUsuario);
            stmt.setLong(6, idMissao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre o usuário " + idUsuario +
                                " e a missão " + idMissao + " não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar a missão do usuário.", e);
        }
    }

    public void deletar(Long idUsuario, Long idMissao) {
        String sql =
                "DELETE FROM T_EV_USU_MIS " +
                        "WHERE id_usuario = ? AND id_missao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idUsuario);
            stmt.setLong(2, idMissao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Associação entre o usuário " + idUsuario +
                                " e a missão " + idMissao + " não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao excluir a missão do usuário.", e);
        }
    }

    private UsuarioMissao mapearUsuarioMissao(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Missao missao = new Missao();
        missao.setId(rs.getLong("id_missao"));

        Date dataInicio = rs.getDate("dt_inicio");
        Date dataConclusao = rs.getDate("dt_conclusao");

        return new UsuarioMissao(
                usuario,
                missao,
                dataInicio != null ? dataInicio.toLocalDate() : null,
                dataConclusao != null ? dataConclusao.toLocalDate() : null,
                rs.getString("st_missao_usuario"),
                rs.getDouble("pg_missao_usuario")
        );
    }

    private void validarUsuarioMissao(UsuarioMissao usuarioMissao) {
        if (usuarioMissao == null) {
            throw new IllegalArgumentException(
                    "Os dados da missão do usuário são obrigatórios.");
        }

        if (usuarioMissao.getUsuario() == null
                || usuarioMissao.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID do usuário.");
        }

        if (usuarioMissao.getMissao() == null
                || usuarioMissao.getMissao().getId() == null) {
            throw new IllegalArgumentException(
                    "É necessário informar o ID da missão.");
        }
    }
}