package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.ContaEnergia;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.Recompensa;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContaEnergiaDao {

    private final DataSource dataSource;

    public ContaEnergiaDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_conta_energia, id_usuario, id_recompensa, " +
                    "nm_titular, cc_conta_energia, nr_instalacao, ms_referencia, " +
                    "vl_conta, vl_subsidiado, rq_comprovante, st_pagamento " +
                    "FROM T_EV_CONTA_ENERGIA ";

    public List<ContaEnergia> listar() {
        String sql = SQL_BASE + "ORDER BY id_conta_energia";

        List<ContaEnergia> contas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                contas.add(mapearContaEnergia(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas de energia.", e);
        }

        return contas;
    }

    public ContaEnergia buscar(Long id) {
        String sql = SQL_BASE + "WHERE id_conta_energia = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearContaEnergia(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta de energia.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Conta de energia não encontrada com o ID: " + id
        );
    }

    public ContaEnergia inserir(ContaEnergia conta) {
        validarContaEnergia(conta);

        String sql =
                "INSERT INTO T_EV_CONTA_ENERGIA (" +
                        "id_conta_energia, id_usuario, id_recompensa, " +
                        "nm_titular, cc_conta_energia, nr_instalacao, " +
                        "ms_referencia, vl_conta, vl_subsidiado, " +
                        "rq_comprovante, st_pagamento" +
                        ") VALUES (" +
                        "SQ_EV_CONTA_ENERGIA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, " +
                        "NVL(?, 0), ?, NVL(?, 'PENDENTE')" +
                        ")";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, new String[]{"id_conta_energia"}
             )) {

            stmt.setLong(1, conta.getUsuario().getId());

            if (conta.getRecompensa() != null
                    && conta.getRecompensa().getId() != null) {
                stmt.setLong(2, conta.getRecompensa().getId());
            } else {
                stmt.setNull(2, Types.NUMERIC);
            }

            stmt.setString(3, conta.getNomeTitular());
            stmt.setString(4, conta.getConcessionaria());
            stmt.setString(5, conta.getNumeroInstalacao());
            stmt.setString(6, conta.getMesReferencia());
            stmt.setDouble(7, conta.getValorConta());

            if (conta.getValorSubsidio() != null) {
                stmt.setDouble(8, conta.getValorSubsidio());
            } else {
                stmt.setNull(8, Types.NUMERIC);
            }

            stmt.setString(9, conta.getArquivoComprovante());
            stmt.setString(10, conta.getStatusPagamento());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conta.setId(generatedKeys.getLong(1));
                }
            }

            if (conta.getId() == null) {
                throw new RuntimeException(
                        "Não foi possível recuperar o ID da conta de energia inserida."
                );
            }

            return buscar(conta.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir conta de energia.", e);
        }
    }

    public ContaEnergia atualizar(Long id, ContaEnergia conta) {
        validarContaEnergia(conta);

        String sql =
                "UPDATE T_EV_CONTA_ENERGIA SET " +
                        "id_usuario = ?, " +
                        "id_recompensa = ?, " +
                        "nm_titular = ?, " +
                        "cc_conta_energia = ?, " +
                        "nr_instalacao = ?, " +
                        "ms_referencia = ?, " +
                        "vl_conta = ?, " +
                        "vl_subsidiado = NVL(?, 0), " +
                        "rq_comprovante = ?, " +
                        "st_pagamento = NVL(?, 'PENDENTE') " +
                        "WHERE id_conta_energia = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, conta.getUsuario().getId());

            if (conta.getRecompensa() != null
                    && conta.getRecompensa().getId() != null) {
                stmt.setLong(2, conta.getRecompensa().getId());
            } else {
                stmt.setNull(2, Types.NUMERIC);
            }

            stmt.setString(3, conta.getNomeTitular());
            stmt.setString(4, conta.getConcessionaria());
            stmt.setString(5, conta.getNumeroInstalacao());
            stmt.setString(6, conta.getMesReferencia());
            stmt.setDouble(7, conta.getValorConta());

            if (conta.getValorSubsidio() != null) {
                stmt.setDouble(8, conta.getValorSubsidio());
            } else {
                stmt.setNull(8, Types.NUMERIC);
            }

            stmt.setString(9, conta.getArquivoComprovante());
            stmt.setString(10, conta.getStatusPagamento());
            stmt.setLong(11, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Conta de energia não encontrada com o ID: " + id
                );
            }

            return buscar(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta de energia.", e);
        }
    }

    public void deletar(Long id) {
        String sql =
                "DELETE FROM T_EV_CONTA_ENERGIA WHERE id_conta_energia = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Conta de energia não encontrada com o ID: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta de energia.", e);
        }
    }

    private ContaEnergia mapearContaEnergia(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id_usuario"));

        Recompensa recompensa = null;
        long idRecompensa = rs.getLong("id_recompensa");

        if (!rs.wasNull()) {
            recompensa = new Recompensa();
            recompensa.setId(idRecompensa);
        }

        return new ContaEnergia(
                rs.getLong("id_conta_energia"),
                usuario,
                recompensa,
                rs.getString("nm_titular"),
                rs.getString("cc_conta_energia"),
                rs.getString("nr_instalacao"),
                rs.getString("ms_referencia"),
                rs.getDouble("vl_conta"),
                rs.getDouble("vl_subsidiado"),
                rs.getString("rq_comprovante"),
                rs.getString("st_pagamento")
        );
    }

    private void validarContaEnergia(ContaEnergia conta) {
        if (conta == null) {
            throw new IllegalArgumentException(
                    "A conta de energia não pode ser nula."
            );
        }

        if (conta.getUsuario() == null
                || conta.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "É obrigatório informar o ID do usuário."
            );
        }

        if (conta.getNomeTitular() == null
                || conta.getNomeTitular().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do titular é obrigatório."
            );
        }

        if (conta.getConcessionaria() == null
                || conta.getConcessionaria().isBlank()) {
            throw new IllegalArgumentException(
                    "A concessionária é obrigatória."
            );
        }

        if (conta.getMesReferencia() == null
                || conta.getMesReferencia().isBlank()) {
            throw new IllegalArgumentException(
                    "O mês de referência é obrigatório."
            );
        }

        if (conta.getMesReferencia().length() > 7) {
            throw new IllegalArgumentException(
                    "O mês de referência deve ter no máximo 7 caracteres."
            );
        }

        if (conta.getValorConta() == null) {
            throw new IllegalArgumentException(
                    "O valor da conta é obrigatório."
            );
        }
    }
}