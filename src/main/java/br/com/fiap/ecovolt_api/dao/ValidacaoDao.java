package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import br.com.fiap.ecovolt_api.model.Validacao;
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
public class ValidacaoDao {

    private final DataSource dataSource;

    public ValidacaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_validacao, id_envio, tp_validacao, st_validacao, " +
                    "ia_resultado, sc_confianca_ia, fr_detectada, cn_repetido, " +
                    "rt_confirmado, cc_aprovada, dt_validacao, ob_validacao " +
                    "FROM T_EV_VALIDACAO ";

    // Converte uma linha do banco em um objeto Validacao.
    private Validacao mapearValidacao(ResultSet rs) throws SQLException {

        EnvioAcao envioAcao = new EnvioAcao();
        envioAcao.setId(rs.getLong("id_envio"));

        double score = rs.getDouble("sc_confianca_ia");
        Double scoreConfiancaIa = rs.wasNull() ? null : score;

        Date dataSql = rs.getDate("dt_validacao");
        LocalDate data = dataSql != null
                ? dataSql.toLocalDate()
                : null;

        return new Validacao(
                rs.getLong("id_validacao"),
                envioAcao,
                rs.getString("tp_validacao"),
                rs.getString("st_validacao"),
                rs.getString("ia_resultado"),
                scoreConfiancaIa,
                converterCharacter(rs.getString("fr_detectada")),
                converterCharacter(rs.getString("cn_repetido")),
                converterCharacter(rs.getString("rt_confirmado")),
                converterCharacter(rs.getString("cc_aprovada")),
                data,
                rs.getString("ob_validacao")
        );
    }

    private Character converterCharacter(String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }

        return valor.charAt(0);
    }

    // Lista todas as validações.
    public List<Validacao> listar() throws SQLException {

        List<Validacao> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_BASE)) {

            while (rs.next()) {
                lista.add(mapearValidacao(rs));
            }
        }

        return lista;
    }

    // Busca uma validação pelo ID.
    public Validacao buscar(long id) throws SQLException {

        String sql = SQL_BASE + "WHERE id_validacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException(
                            "Validação não encontrada."
                    );
                }

                return mapearValidacao(rs);
            }
        }
    }

    // Insere uma validação.
    public void inserir(Validacao validacao) throws SQLException {

        validarValidacao(validacao);

        String sqlSequencia =
                "SELECT SQ_EV_VALIDACAO.NEXTVAL FROM DUAL";

        String sqlInsert =
                "INSERT INTO T_EV_VALIDACAO (" +
                        "id_validacao, id_envio, tp_validacao, st_validacao, " +
                        "ia_resultado, sc_confianca_ia, fr_detectada, " +
                        "cn_repetido, rt_confirmado, cc_aprovada, " +
                        "dt_validacao, ob_validacao" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "NVL(?, SYSDATE), ?)";

        try (Connection conn = dataSource.getConnection()) {

            long idGerado;

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlSequencia)) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID da validação."
                    );
                }

                idGerado = rs.getLong(1);
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {

                stmt.setLong(1, idGerado);
                stmt.setLong(2, validacao.getEnvioAcao().getId());
                stmt.setString(3, validacao.getTipo());

                stmt.setString(
                        4,
                        validacao.getStatus() != null
                                ? validacao.getStatus()
                                : "PENDENTE"
                );

                stmt.setString(5, validacao.getResultadoIa());

                if (validacao.getScoreConfiancaIa() != null) {
                    stmt.setDouble(
                            6,
                            validacao.getScoreConfiancaIa()
                    );
                } else {
                    stmt.setNull(6, Types.NUMERIC);
                }

                stmt.setString(
                        7,
                        validacao.getFraudeDetectada() != null
                                ? validacao.getFraudeDetectada().toString()
                                : "N"
                );

                stmt.setString(
                        8,
                        validacao.getConteudoRepetido() != null
                                ? validacao.getConteudoRepetido().toString()
                                : "N"
                );

                if (validacao.getTextoConfirmado() != null) {
                    stmt.setString(
                            9,
                            validacao.getTextoConfirmado().toString()
                    );
                } else {
                    stmt.setNull(9, Types.CHAR);
                }

                stmt.setString(
                        10,
                        validacao.getOcorrenciaAprovada() != null
                                ? validacao.getOcorrenciaAprovada().toString()
                                : "N"
                );

                if (validacao.getData() != null) {
                    stmt.setDate(
                            11,
                            Date.valueOf(validacao.getData())
                    );
                } else {
                    stmt.setNull(11, Types.DATE);
                }

                stmt.setString(12, validacao.getObservacao());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException(
                            "Não foi possível inserir a validação."
                    );
                }

                validacao.setId(idGerado);
            }
        }
    }

    // Atualiza uma validação existente.
    public void atualizar(Validacao validacao) throws SQLException {

        validarValidacao(validacao);

        if (validacao.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da validação é obrigatório para atualização."
            );
        }

        String sql =
                "UPDATE T_EV_VALIDACAO SET " +
                        "id_envio = ?, tp_validacao = ?, st_validacao = ?, " +
                        "ia_resultado = ?, sc_confianca_ia = ?, " +
                        "fr_detectada = ?, cn_repetido = ?, rt_confirmado = ?, " +
                        "cc_aprovada = ?, dt_validacao = NVL(?, SYSDATE), " +
                        "ob_validacao = ? " +
                        "WHERE id_validacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, validacao.getEnvioAcao().getId());
            stmt.setString(2, validacao.getTipo());

            stmt.setString(
                    3,
                    validacao.getStatus() != null
                            ? validacao.getStatus()
                            : "PENDENTE"
            );

            stmt.setString(4, validacao.getResultadoIa());

            if (validacao.getScoreConfiancaIa() != null) {
                stmt.setDouble(
                        5,
                        validacao.getScoreConfiancaIa()
                );
            } else {
                stmt.setNull(5, Types.NUMERIC);
            }

            stmt.setString(
                    6,
                    validacao.getFraudeDetectada() != null
                            ? validacao.getFraudeDetectada().toString()
                            : "N"
            );

            stmt.setString(
                    7,
                    validacao.getConteudoRepetido() != null
                            ? validacao.getConteudoRepetido().toString()
                            : "N"
            );

            if (validacao.getTextoConfirmado() != null) {
                stmt.setString(
                        8,
                        validacao.getTextoConfirmado().toString()
                );
            } else {
                stmt.setNull(8, Types.CHAR);
            }

            stmt.setString(
                    9,
                    validacao.getOcorrenciaAprovada() != null
                            ? validacao.getOcorrenciaAprovada().toString()
                            : "N"
            );

            if (validacao.getData() != null) {
                stmt.setDate(
                        10,
                        Date.valueOf(validacao.getData())
                );
            } else {
                stmt.setNull(10, Types.DATE);
            }

            stmt.setString(11, validacao.getObservacao());
            stmt.setLong(12, validacao.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Validação não encontrada."
                );
            }
        }
    }

    // Exclui uma validação pelo ID.
    public void deletar(Long id) throws SQLException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "O ID da validação não pode ser nulo."
            );
        }

        String sql =
                "DELETE FROM T_EV_VALIDACAO WHERE id_validacao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Validação não encontrada."
                );
            }
        }
    }

    private void validarValidacao(Validacao validacao) {

        if (validacao == null) {
            throw new IllegalArgumentException(
                    "Os dados da validação são obrigatórios."
            );
        }

        if (validacao.getEnvioAcao() == null
                || validacao.getEnvioAcao().getId() == null) {
            throw new IllegalArgumentException(
                    "A validação precisa estar associada a um envio de ação com ID."
            );
        }

        if (validacao.getTipo() == null
                || validacao.getTipo().isBlank()) {
            throw new IllegalArgumentException(
                    "O tipo da validação é obrigatório."
            );
        }
    }
}