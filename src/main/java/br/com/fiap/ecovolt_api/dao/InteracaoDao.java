package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Interacao;
import br.com.fiap.ecovolt_api.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InteracaoDao {

    private final DataSource dataSource;

    public InteracaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_interacao, id_usuario, tp_interacao, " +
                    "dt_interacao, ps_interacao, nt_valida, " +
                    "sp_detectado, cn_relacionado " +
                    "FROM T_EV_INTERACAO ";

    // Converte uma linha do banco em um objeto Interacao.
    private Interacao mapearInteracao(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Date dataSql = rs.getDate("dt_interacao");
        LocalDate data = dataSql != null
                ? dataSql.toLocalDate()
                : null;

        double pesoBanco = rs.getDouble("ps_interacao");
        Double peso = rs.wasNull() ? null : pesoBanco;

        return new Interacao(
                rs.getLong("id_interacao"),
                usuario,
                rs.getString("tp_interacao"),
                data,
                peso,
                converterCharacter(rs.getString("nt_valida")),
                converterCharacter(rs.getString("sp_detectado")),
                rs.getString("cn_relacionado")
        );
    }

    private Character converterCharacter(String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }

        return valor.charAt(0);
    }

    // Lista todas as interações.
    public List<Interacao> listar() throws SQLException {

        List<Interacao> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_BASE)) {

            while (rs.next()) {
                lista.add(mapearInteracao(rs));
            }
        }

        return lista;
    }

    // Busca uma interação pelo ID.
    public Interacao buscar(long id) throws SQLException {

        String sql = SQL_BASE + "WHERE id_interacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException(
                            "Interação não encontrada."
                    );
                }

                return mapearInteracao(rs);
            }
        }
    }

    // Insere uma interação.
    public void inserir(Interacao interacao) throws SQLException {

        validarInteracao(interacao);

        String sqlSequencia =
                "SELECT SQ_EV_INTERACAO.NEXTVAL FROM DUAL";

        String sqlInsert =
                "INSERT INTO T_EV_INTERACAO (" +
                        "id_interacao, id_usuario, tp_interacao, dt_interacao, " +
                        "ps_interacao, nt_valida, sp_detectado, cn_relacionado" +
                        ") VALUES (?, ?, ?, NVL(?, SYSDATE), NVL(?, 1), " +
                        "NVL(?, 'S'), NVL(?, 'N'), ?)";

        try (Connection conn = dataSource.getConnection()) {

            long idGerado;

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlSequencia)) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID da interação."
                    );
                }

                idGerado = rs.getLong(1);
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {

                stmt.setLong(1, idGerado);
                stmt.setLong(2, interacao.getUsuario().getId());
                stmt.setString(3, interacao.getTipo());

                if (interacao.getData() != null) {
                    stmt.setDate(
                            4,
                            Date.valueOf(interacao.getData())
                    );
                } else {
                    stmt.setNull(4, Types.DATE);
                }

                if (interacao.getPeso() != null) {
                    stmt.setDouble(5, interacao.getPeso());
                } else {
                    stmt.setNull(5, Types.NUMERIC);
                }

                if (interacao.getInteracaoValida() != null) {
                    stmt.setString(
                            6,
                            interacao.getInteracaoValida().toString()
                    );
                } else {
                    stmt.setNull(6, Types.CHAR);
                }

                if (interacao.getSpamDetectado() != null) {
                    stmt.setString(
                            7,
                            interacao.getSpamDetectado().toString()
                    );
                } else {
                    stmt.setNull(7, Types.CHAR);
                }

                stmt.setString(8, interacao.getConteudoRelacionado());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException(
                            "Não foi possível inserir a interação."
                    );
                }

                interacao.setId(idGerado);
            }
        }
    }

    // Atualiza uma interação existente.
    public void atualizar(Interacao interacao) throws SQLException {

        validarInteracao(interacao);

        if (interacao.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da interação é obrigatório para atualização."
            );
        }

        String sql =
                "UPDATE T_EV_INTERACAO SET " +
                        "id_usuario = ?, tp_interacao = ?, " +
                        "dt_interacao = NVL(?, SYSDATE), " +
                        "ps_interacao = NVL(?, 1), " +
                        "nt_valida = NVL(?, 'S'), " +
                        "sp_detectado = NVL(?, 'N'), " +
                        "cn_relacionado = ? " +
                        "WHERE id_interacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, interacao.getUsuario().getId());
            stmt.setString(2, interacao.getTipo());

            if (interacao.getData() != null) {
                stmt.setDate(
                        3,
                        Date.valueOf(interacao.getData())
                );
            } else {
                stmt.setNull(3, Types.DATE);
            }

            if (interacao.getPeso() != null) {
                stmt.setDouble(4, interacao.getPeso());
            } else {
                stmt.setNull(4, Types.NUMERIC);
            }

            if (interacao.getInteracaoValida() != null) {
                stmt.setString(
                        5,
                        interacao.getInteracaoValida().toString()
                );
            } else {
                stmt.setNull(5, Types.CHAR);
            }

            if (interacao.getSpamDetectado() != null) {
                stmt.setString(
                        6,
                        interacao.getSpamDetectado().toString()
                );
            } else {
                stmt.setNull(6, Types.CHAR);
            }

            stmt.setString(7, interacao.getConteudoRelacionado());
            stmt.setLong(8, interacao.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Interação não encontrada."
                );
            }
        }
    }

    // Exclui uma interação pelo ID.
    public void deletar(Long id) throws SQLException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "O ID da interação não pode ser nulo."
            );
        }

        String sql =
                "DELETE FROM T_EV_INTERACAO WHERE id_interacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Interação não encontrada."
                );
            }
        }
    }

    private void validarInteracao(Interacao interacao) {

        if (interacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da interação são obrigatórios."
            );
        }

        if (interacao.getUsuario() == null
                || interacao.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "A interação precisa estar associada a um usuário com ID."
            );
        }

        if (interacao.getTipo() == null
                || interacao.getTipo().isBlank()) {
            throw new IllegalArgumentException(
                    "O tipo da interação é obrigatório."
            );
        }
    }
}