package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import br.com.fiap.ecovolt_api.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnvioAcaoDao {

    private final DataSource dataSource;

    public EnvioAcaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<EnvioAcao> listar() {
        List<EnvioAcao> envios = new ArrayList<>();

        String sql = """
                SELECT id_envio,
                       T_EV_USUARIO_id_usuario,
                       T_EV_ACAO_SUSTENTAVEIS_id_acao,
                       dt_envio,
                       tp_evidencia,
                       rq_evidencia,
                       st_envio,
                       pt_prevista,
                       ob_usuario
                FROM T_EV_ENVIO_ACAO
                ORDER BY id_envio
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                envios.add(mapearEnvio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar envios de ações.", e
            );
        }

        return envios;
    }

    public EnvioAcao buscar(long id) {
        String sql = """
                SELECT id_envio,
                       T_EV_USUARIO_id_usuario,
                       T_EV_ACAO_SUSTENTAVEIS_id_acao,
                       dt_envio,
                       tp_evidencia,
                       rq_evidencia,
                       st_envio,
                       pt_prevista,
                       ob_usuario
                FROM T_EV_ENVIO_ACAO
                WHERE id_envio = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEnvio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar envio de ação.", e
            );
        }

        throw new EntidadeNaoEncontradaException(
                "Envio de ação não encontrado: " + id
        );
    }

    public EnvioAcao inserir(EnvioAcao envio) {
        validarEnvio(envio);

        String sqlSequencia =
                "SELECT SQ_EV_ENVIO_ACAO.NEXTVAL FROM DUAL";

        String sql = """
                INSERT INTO T_EV_ENVIO_ACAO (
                    id_envio,
                    T_EV_USUARIO_id_usuario,
                    T_EV_ACAO_SUSTENTAVEIS_id_acao,
                    dt_envio,
                    tp_evidencia,
                    rq_evidencia,
                    st_envio,
                    pt_prevista,
                    ob_usuario
                ) VALUES (
                    ?, ?, ?, NVL(?, SYSDATE), ?, ?, NVL(?, 'PENDENTE'), ?, ?
                )
                """;

        try (Connection conn = dataSource.getConnection()) {
            long id;

            try (Statement stmtId = conn.createStatement();
                 ResultSet rs = stmtId.executeQuery(sqlSequencia)) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID do envio."
                    );
                }

                id = rs.getLong(1);
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.setLong(2, envio.getUsuario().getId());
                stmt.setLong(3, envio.getAcaoSustentavel().getId());

                if (envio.getDataEnvio() != null) {
                    stmt.setTimestamp(
                            4,
                            Timestamp.valueOf(envio.getDataEnvio())
                    );
                } else {
                    stmt.setNull(4, Types.TIMESTAMP);
                }

                stmt.setString(5, envio.getTipoEvidencia());
                stmt.setString(6, envio.getArquivoEvidencia());
                stmt.setString(7, envio.getStatus());

                if (envio.getPontuacaoPrevista() != null) {
                    stmt.setInt(8, envio.getPontuacaoPrevista());
                } else {
                    stmt.setNull(8, Types.NUMERIC);
                }

                stmt.setString(9, envio.getObservacaoUsuario());

                stmt.executeUpdate();

                envio.setId(id);
            }

            return envio;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao inserir envio de ação.", e
            );
        }
    }

    public void atualizar(long id, EnvioAcao envio) {
        validarEnvio(envio);

        String sql = """
                UPDATE T_EV_ENVIO_ACAO
                SET T_EV_USUARIO_id_usuario = ?,
                    T_EV_ACAO_SUSTENTAVEIS_id_acao = ?,
                    dt_envio = ?,
                    tp_evidencia = ?,
                    rq_evidencia = ?,
                    st_envio = ?,
                    pt_prevista = ?,
                    ob_usuario = ?
                WHERE id_envio = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, envio.getUsuario().getId());
            stmt.setLong(2, envio.getAcaoSustentavel().getId());

            if (envio.getDataEnvio() != null) {
                stmt.setTimestamp(
                        3,
                        Timestamp.valueOf(envio.getDataEnvio())
                );
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            stmt.setString(4, envio.getTipoEvidencia());
            stmt.setString(5, envio.getArquivoEvidencia());
            stmt.setString(6, envio.getStatus());

            if (envio.getPontuacaoPrevista() != null) {
                stmt.setInt(7, envio.getPontuacaoPrevista());
            } else {
                stmt.setNull(7, Types.NUMERIC);
            }

            stmt.setString(8, envio.getObservacaoUsuario());
            stmt.setLong(9, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Envio de ação não encontrado: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar envio de ação.", e
            );
        }
    }

    public void deletar(long id) {
        String sql =
                "DELETE FROM T_EV_ENVIO_ACAO WHERE id_envio = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Envio de ação não encontrado: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao excluir envio de ação.", e
            );
        }
    }

    private EnvioAcao mapearEnvio(ResultSet rs)
            throws SQLException {

        EnvioAcao envio = new EnvioAcao();

        envio.setId(rs.getLong("id_envio"));

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("T_EV_USUARIO_id_usuario"));
        envio.setUsuario(usuario);

        AcaoSustentavel acao = new AcaoSustentavel();
        acao.setId(
                rs.getLong("T_EV_ACAO_SUSTENTAVEIS_id_acao")
        );
        envio.setAcaoSustentavel(acao);

        Timestamp data = rs.getTimestamp("dt_envio");

        if (data != null) {
            envio.setDataEnvio(data.toLocalDateTime());
        }

        envio.setTipoEvidencia(rs.getString("tp_evidencia"));
        envio.setArquivoEvidencia(rs.getString("rq_evidencia"));
        envio.setStatus(rs.getString("st_envio"));

        int pontuacao = rs.getInt("pt_prevista");

        envio.setPontuacaoPrevista(
                rs.wasNull() ? null : pontuacao
        );

        envio.setObservacaoUsuario(
                rs.getString("ob_usuario")
        );

        return envio;
    }

    private void validarEnvio(EnvioAcao envio) {
        if (envio == null) {
            throw new IllegalArgumentException(
                    "Os dados do envio devem ser informados."
            );
        }

        if (envio.getUsuario() == null
                || envio.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do usuário deve ser informado."
            );
        }

        if (envio.getAcaoSustentavel() == null
                || envio.getAcaoSustentavel().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da ação sustentável deve ser informado."
            );
        }
    }
}