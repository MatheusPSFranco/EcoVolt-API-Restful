package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Nivel;
import br.com.fiap.ecovolt_api.model.Tier;
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
public class UsuarioDao {

    private final DataSource dataSource;

    public UsuarioDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SQL_BASE =
            "SELECT id_usuario, nm_usuario, em_usuario, sn_hash, " +
                    "dt_cadastro, st_usuario, tp_usuario, xp_total, pt_total, " +
                    "dt_ultimo_login, at_usuario, id_tier, id_nivel " +
                    "FROM T_EV_USUARIO ";

    // Converte uma linha do banco em um objeto Usuario.
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        Date dataCadastroSql = rs.getDate("dt_cadastro");
        LocalDate dataCadastro = dataCadastroSql != null
                ? dataCadastroSql.toLocalDate()
                : null;

        Date dataLoginSql = rs.getDate("dt_ultimo_login");
        LocalDate dataUltimoLogin = dataLoginSql != null
                ? dataLoginSql.toLocalDate()
                : null;

        String ativoString = rs.getString("at_usuario");
        char ativo = ativoString != null && !ativoString.isEmpty()
                ? ativoString.charAt(0)
                : '\0';

        Tier tier = null;
        long idTier = rs.getLong("id_tier");

        if (!rs.wasNull()) {
            tier = new Tier();
            tier.setId(idTier);
        }

        Nivel nivel = null;
        long idNivel = rs.getLong("id_nivel");

        if (!rs.wasNull()) {
            nivel = new Nivel();
            nivel.setId(idNivel);
        }

        return new Usuario(
                rs.getLong("id_usuario"),
                rs.getString("nm_usuario"),
                rs.getString("em_usuario"),
                rs.getString("sn_hash"),
                dataCadastro,
                rs.getString("st_usuario"),
                rs.getString("tp_usuario"),
                rs.getInt("xp_total"),
                rs.getInt("pt_total"),
                dataUltimoLogin,
                ativo,
                tier,
                nivel
        );
    }

    // Lista todos os usuários.
    public List<Usuario> listar() throws SQLException {

        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_BASE)) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        }

        return lista;
    }

    // Busca um usuário pelo ID.
    public Usuario buscar(long id) throws SQLException {

        String sql = SQL_BASE + "WHERE id_usuario = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException(
                            "Usuário não encontrado."
                    );
                }

                return mapearUsuario(rs);
            }
        }
    }

    // Insere um usuário e atribui o ID gerado ao objeto.
    public void inserir(Usuario usuario) throws SQLException {

        validarUsuario(usuario);
        validarRelacionamentos(usuario);

        String sqlSequencia =
                "SELECT SQ_EV_USUARIO.NEXTVAL FROM DUAL";

        String sqlInsert =
                "INSERT INTO T_EV_USUARIO " +
                        "(id_usuario, nm_usuario, em_usuario, sn_hash, " +
                        "dt_cadastro, st_usuario, tp_usuario, xp_total, " +
                        "pt_total, dt_ultimo_login, at_usuario, id_tier, id_nivel) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {

            long idGerado;

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlSequencia)) {

                if (!rs.next()) {
                    throw new SQLException(
                            "Não foi possível gerar o ID do usuário."
                    );
                }

                idGerado = rs.getLong(1);
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {

                stmt.setLong(1, idGerado);
                stmt.setString(2, usuario.getNome());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getSenhaHash());

                if (usuario.getDataCadastro() != null) {
                    stmt.setDate(
                            5,
                            Date.valueOf(usuario.getDataCadastro())
                    );
                } else {
                    stmt.setNull(5, Types.DATE);
                }

                stmt.setString(6, usuario.getStatus());
                stmt.setString(7, usuario.getTipo());
                stmt.setInt(8, usuario.getXpTotal());
                stmt.setInt(9, usuario.getPontuacaoTotal());

                if (usuario.getDataUltimoLogin() != null) {
                    stmt.setDate(
                            10,
                            Date.valueOf(usuario.getDataUltimoLogin())
                    );
                } else {
                    stmt.setNull(10, Types.DATE);
                }

                stmt.setString(11, String.valueOf(usuario.getAtivo()));

                if (usuario.getTier() != null) {
                    stmt.setLong(12, usuario.getTier().getId());
                } else {
                    stmt.setNull(12, Types.NUMERIC);
                }

                if (usuario.getNivel() != null) {
                    stmt.setLong(13, usuario.getNivel().getId());
                } else {
                    stmt.setNull(13, Types.NUMERIC);
                }

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException(
                            "Não foi possível inserir o usuário."
                    );
                }

                usuario.setId(idGerado);
            }
        }
    }

    // Atualiza os dados de um usuário existente.
    public void atualizar(Usuario usuario) throws SQLException {

        validarUsuario(usuario);
        validarRelacionamentos(usuario);

        String sql =
                "UPDATE T_EV_USUARIO SET " +
                        "nm_usuario = ?, em_usuario = ?, sn_hash = ?, " +
                        "dt_cadastro = ?, st_usuario = ?, tp_usuario = ?, " +
                        "xp_total = ?, pt_total = ?, dt_ultimo_login = ?, " +
                        "at_usuario = ?, id_tier = ?, id_nivel = ? " +
                        "WHERE id_usuario = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenhaHash());

            if (usuario.getDataCadastro() != null) {
                stmt.setDate(
                        4,
                        Date.valueOf(usuario.getDataCadastro())
                );
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, usuario.getStatus());
            stmt.setString(6, usuario.getTipo());
            stmt.setInt(7, usuario.getXpTotal());
            stmt.setInt(8, usuario.getPontuacaoTotal());

            if (usuario.getDataUltimoLogin() != null) {
                stmt.setDate(
                        9,
                        Date.valueOf(usuario.getDataUltimoLogin())
                );
            } else {
                stmt.setNull(9, Types.DATE);
            }

            stmt.setString(10, String.valueOf(usuario.getAtivo()));

            if (usuario.getTier() != null) {
                stmt.setLong(11, usuario.getTier().getId());
            } else {
                stmt.setNull(11, Types.NUMERIC);
            }

            if (usuario.getNivel() != null) {
                stmt.setLong(12, usuario.getNivel().getId());
            } else {
                stmt.setNull(12, Types.NUMERIC);
            }

            stmt.setLong(13, usuario.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Usuário não encontrado."
                );
            }
        }
    }

    // Exclui um usuário pelo ID.
    public void deletar(Long id) throws SQLException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "O ID do usuário não pode ser nulo."
            );
        }

        String sql =
                "DELETE FROM T_EV_USUARIO WHERE id_usuario = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Usuário não encontrado."
                );
            }
        }
    }

    private void validarUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Os dados do usuário são obrigatórios."
            );
        }
    }

    private void validarRelacionamentos(Usuario usuario) {

        if (usuario.getTier() != null
                && usuario.getTier().getId() == null) {
            throw new IllegalArgumentException(
                    "O Tier informado precisa ter um ID."
            );
        }

        if (usuario.getNivel() != null
                && usuario.getNivel().getId() == null) {
            throw new IllegalArgumentException(
                    "O nível informado precisa ter um ID."
            );
        }
    }
}