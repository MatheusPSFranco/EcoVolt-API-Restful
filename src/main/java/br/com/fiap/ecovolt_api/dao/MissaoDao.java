package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.Missao;
import br.com.fiap.ecovolt_api.model.Nivel;
import br.com.fiap.ecovolt_api.model.Tier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MissaoDao {

    private final DataSource dataSource;

    public MissaoDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Missao> listar() {
        String sql = """
                SELECT id_missao, nm_missao, dc_missao, df_missao,
                       pt_recompensa, xp_recompensa, qt_semanal,
                       tp_missao, st_missao, id_tier, id_nivel
                FROM T_EV_MISSAO
                """;

        List<Missao> missoes = new ArrayList<>();

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                missoes.add(mapearMissao(rs));
            }

            return missoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar missões.", e);
        }
    }

    public Missao buscar(long id) {
        String sql = """
                SELECT id_missao, nm_missao, dc_missao, df_missao,
                       pt_recompensa, xp_recompensa, qt_semanal,
                       tp_missao, st_missao, id_tier, id_nivel
                FROM T_EV_MISSAO
                WHERE id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMissao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar missão.", e);
        }

        throw new EntidadeNaoEncontradaException(
                "Missão não encontrada."
        );
    }

    public Missao inserir(Missao missao) {
        validarMissao(missao);

        String sqlSequencia =
                "SELECT SQ_EV_MISSAO.NEXTVAL FROM DUAL";

        String sql = """
                INSERT INTO T_EV_MISSAO (
                    id_missao,
                    nm_missao,
                    dc_missao,
                    df_missao,
                    pt_recompensa,
                    xp_recompensa,
                    qt_semanal,
                    tp_missao,
                    st_missao,
                    id_tier,
                    id_nivel
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = dataSource.getConnection()) {

            // Obtém o próximo ID da sequência Oracle.
            try (Statement stmtSequencia = conexao.createStatement();
                 ResultSet rs = stmtSequencia.executeQuery(sqlSequencia)) {

                if (rs.next()) {
                    missao.setId(rs.getLong(1));
                } else {
                    throw new RuntimeException(
                            "Não foi possível gerar o ID da missão."
                    );
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, missao.getId());
                stmt.setString(2, missao.getNome());
                stmt.setString(3, missao.getDescricao());
                stmt.setString(4, missao.getDificuldade());
                stmt.setInt(5, missao.getPontuacaoRecompensa());
                stmt.setInt(6, missao.getXpRecompensa());

                // Quantidade semanal: aceita NULL sem confundir com zero.
                if (missao.getQuantidadeSemanal() != null) {
                    stmt.setInt(7, missao.getQuantidadeSemanal());
                } else {
                    stmt.setNull(7, Types.NUMERIC);
                }

                stmt.setString(8, missao.getTipo());
                stmt.setString(
                        9,
                        missao.isStatus() ? "ATIVA" : "INATIVA"
                );

                definirIdTier(stmt, 10, missao.getTier());
                definirIdNivel(stmt, 11, missao.getNivel());

                stmt.executeUpdate();
            }

            return missao;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir missão.", e);
        }
    }

    public void atualizar(Missao missao) {
        validarMissao(missao);

        if (missao.getId() == null) {
            throw new IllegalArgumentException(
                    "O ID da missão é obrigatório para atualização."
            );
        }

        String sql = """
                UPDATE T_EV_MISSAO
                SET nm_missao = ?,
                    dc_missao = ?,
                    df_missao = ?,
                    pt_recompensa = ?,
                    xp_recompensa = ?,
                    qt_semanal = ?,
                    tp_missao = ?,
                    st_missao = ?,
                    id_tier = ?,
                    id_nivel = ?
                WHERE id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, missao.getNome());
            stmt.setString(2, missao.getDescricao());
            stmt.setString(3, missao.getDificuldade());
            stmt.setInt(4, missao.getPontuacaoRecompensa());
            stmt.setInt(5, missao.getXpRecompensa());

            if (missao.getQuantidadeSemanal() != null) {
                stmt.setInt(6, missao.getQuantidadeSemanal());
            } else {
                stmt.setNull(6, Types.NUMERIC);
            }

            stmt.setString(7, missao.getTipo());
            stmt.setString(
                    8,
                    missao.isStatus() ? "ATIVA" : "INATIVA"
            );

            definirIdTier(stmt, 9, missao.getTier());
            definirIdNivel(stmt, 10, missao.getNivel());

            stmt.setLong(11, missao.getId());

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar missão.", e);
        }
    }

    public void deletar(long id) {
        String sql = """
                DELETE FROM T_EV_MISSAO
                WHERE id_missao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAlteradas = stmt.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException(
                        "Missão não encontrada."
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar missão.", e);
        }
    }

    private Missao mapearMissao(ResultSet rs) throws SQLException {
        Missao missao = new Missao();

        missao.setId(rs.getLong("id_missao"));
        missao.setNome(rs.getString("nm_missao"));
        missao.setDescricao(rs.getString("dc_missao"));
        missao.setDificuldade(rs.getString("df_missao"));
        missao.setPontuacaoRecompensa(
                rs.getInt("pt_recompensa")
        );
        missao.setXpRecompensa(
                rs.getInt("xp_recompensa")
        );

        int quantidadeSemanal = rs.getInt("qt_semanal");

        if (rs.wasNull()) {
            missao.setQuantidadeSemanal(null);
        } else {
            missao.setQuantidadeSemanal(quantidadeSemanal);
        }

        // O tipo é texto e pode conter valores como "MENSAL".
        missao.setTipo(rs.getString("tp_missao"));

        missao.setStatus(
                "ATIVA".equalsIgnoreCase(rs.getString("st_missao"))
        );

        long idTier = rs.getLong("id_tier");

        if (!rs.wasNull()) {
            Tier tier = new Tier();
            tier.setId(idTier);
            missao.setTier(tier);
        }

        long idNivel = rs.getLong("id_nivel");

        if (!rs.wasNull()) {
            Nivel nivel = new Nivel();
            nivel.setId(idNivel);
            missao.setNivel(nivel);
        }

        return missao;
    }

    private void definirIdTier(
            PreparedStatement stmt,
            int indice,
            Tier tier
    ) throws SQLException {

        if (tier != null && tier.getId() != null) {
            stmt.setLong(indice, tier.getId());
        } else {
            stmt.setNull(indice, Types.NUMERIC);
        }
    }

    private void definirIdNivel(
            PreparedStatement stmt,
            int indice,
            Nivel nivel
    ) throws SQLException {

        if (nivel != null && nivel.getId() != null) {
            stmt.setLong(indice, nivel.getId());
        } else {
            stmt.setNull(indice, Types.NUMERIC);
        }
    }

    private void validarMissao(Missao missao) {
        if (missao == null) {
            throw new IllegalArgumentException(
                    "Os dados da missão são obrigatórios."
            );
        }
    }
}