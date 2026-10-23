package br.com.fiap.ecovolt_api.dao;

import br.com.fiap.ecovolt_api.exception.EntidadeNaoEncontradaException;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import br.com.fiap.ecovolt_api.model.Ods;
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
public class AcaoSustentavelDao {

    private final DataSource dataSource;

    public AcaoSustentavelDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<AcaoSustentavel> listar() {
        List<AcaoSustentavel> lista = new ArrayList<>();

        String sql = """
                SELECT id_acao, id_ods, nm_acao, dc_acao_sustentavel,
                       df_acao_sustentavel, im_ambiental, sf_necessario,
                       fq_recomendada, pt_base, lm_diario, st_acao
                FROM T_EV_ACAO_SUSTENTAVEIS
                """;

        try (Connection conexao = dataSource.getConnection();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AcaoSustentavel acao = new AcaoSustentavel();

                acao.setId(rs.getLong("id_acao"));

                long idOds = rs.getLong("id_ods");
                if (!rs.wasNull()) {
                    Ods ods = new Ods();
                    ods.setId(idOds);
                    acao.setOds(ods);
                }

                acao.setNome(rs.getString("nm_acao"));
                acao.setDescricao(rs.getString("dc_acao_sustentavel"));
                acao.setDificuldade(rs.getString("df_acao_sustentavel"));

                double impacto = rs.getDouble("im_ambiental");
                acao.setImpactoAmbiental(
                        rs.wasNull() ? null : impacto
                );

                double esforco = rs.getDouble("sf_necessario");
                acao.setEsforcoNecessario(
                        rs.wasNull() ? null : esforco
                );

                acao.setFrequenciaRecomendada(rs.getString("fq_recomendada"));

                int pontuacao = rs.getInt("pt_base");
                acao.setPontuacaoBase(
                        rs.wasNull() ? null : pontuacao
                );

                int limiteDiario = rs.getInt("lm_diario");
                acao.setLimiteDiario(
                        rs.wasNull() ? null : limiteDiario
                );

                acao.setStatus(rs.getString("st_acao"));

                lista.add(acao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ações sustentáveis.", e);
        }

        return lista;
    }

    public AcaoSustentavel buscar(long id) {
        String sql = """
                SELECT id_acao, id_ods, nm_acao, dc_acao_sustentavel,
                       df_acao_sustentavel, im_ambiental, sf_necessario,
                       fq_recomendada, pt_base, lm_diario, st_acao
                FROM T_EV_ACAO_SUSTENTAVEIS
                WHERE id_acao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AcaoSustentavel acao = new AcaoSustentavel();

                    acao.setId(rs.getLong("id_acao"));

                    long idOds = rs.getLong("id_ods");
                    if (!rs.wasNull()) {
                        Ods ods = new Ods();
                        ods.setId(idOds);
                        acao.setOds(ods);
                    }

                    acao.setNome(rs.getString("nm_acao"));
                    acao.setDescricao(rs.getString("dc_acao_sustentavel"));
                    acao.setDificuldade(rs.getString("df_acao_sustentavel"));

                    double impacto = rs.getDouble("im_ambiental");
                    acao.setImpactoAmbiental(
                            rs.wasNull() ? null : impacto
                    );

                    double esforco = rs.getDouble("sf_necessario");
                    acao.setEsforcoNecessario(
                            rs.wasNull() ? null : esforco
                    );

                    acao.setFrequenciaRecomendada(rs.getString("fq_recomendada"));

                    int pontuacao = rs.getInt("pt_base");
                    acao.setPontuacaoBase(
                            rs.wasNull() ? null : pontuacao
                    );

                    int limiteDiario = rs.getInt("lm_diario");
                    acao.setLimiteDiario(
                            rs.wasNull() ? null : limiteDiario
                    );

                    acao.setStatus(rs.getString("st_acao"));

                    return acao;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ação sustentável.", e);
        }

        throw new EntidadeNaoEncontradaException("Ação sustentável não encontrada.");
    }

    public void inserir(AcaoSustentavel acao) {
        String sql = """
                INSERT INTO T_EV_ACAO_SUSTENTAVEIS (
                    id_acao, id_ods, nm_acao, dc_acao_sustentavel,
                    df_acao_sustentavel, im_ambiental, sf_necessario,
                    fq_recomendada, pt_base, lm_diario, st_acao
                ) VALUES (
                    SQ_EV_ACAO_SUSTENTAVEIS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                )
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(
                     sql, new String[]{"id_acao"})) {

            if (acao.getOds() != null && acao.getOds().getId() != null) {
                stmt.setLong(1, acao.getOds().getId());
            } else {
                stmt.setNull(1, Types.NUMERIC);
            }

            stmt.setString(2, acao.getNome());
            stmt.setString(3, acao.getDescricao());
            stmt.setString(4, acao.getDificuldade());

            if (acao.getImpactoAmbiental() != null) {
                stmt.setDouble(5, acao.getImpactoAmbiental());
            } else {
                stmt.setNull(5, Types.NUMERIC);
            }

            if (acao.getEsforcoNecessario() != null) {
                stmt.setDouble(6, acao.getEsforcoNecessario());
            } else {
                stmt.setNull(6, Types.NUMERIC);
            }

            stmt.setString(7, acao.getFrequenciaRecomendada());

            if (acao.getPontuacaoBase() != null) {
                stmt.setInt(8, acao.getPontuacaoBase());
            } else {
                stmt.setNull(8, Types.NUMERIC);
            }

            if (acao.getLimiteDiario() != null) {
                stmt.setInt(9, acao.getLimiteDiario());
            } else {
                stmt.setNull(9, Types.NUMERIC);
            }

            stmt.setString(10, acao.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    acao.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ação sustentável.", e);
        }
    }

    public void atualizar(AcaoSustentavel acao) {
        String sql = """
                UPDATE T_EV_ACAO_SUSTENTAVEIS SET
                    id_ods = ?,
                    nm_acao = ?,
                    dc_acao_sustentavel = ?,
                    df_acao_sustentavel = ?,
                    im_ambiental = ?,
                    sf_necessario = ?,
                    fq_recomendada = ?,
                    pt_base = ?,
                    lm_diario = ?,
                    st_acao = ?
                WHERE id_acao = ?
                """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (acao.getOds() != null && acao.getOds().getId() != null) {
                stmt.setLong(1, acao.getOds().getId());
            } else {
                stmt.setNull(1, Types.NUMERIC);
            }

            stmt.setString(2, acao.getNome());
            stmt.setString(3, acao.getDescricao());
            stmt.setString(4, acao.getDificuldade());

            if (acao.getImpactoAmbiental() != null) {
                stmt.setDouble(5, acao.getImpactoAmbiental());
            } else {
                stmt.setNull(5, Types.NUMERIC);
            }

            if (acao.getEsforcoNecessario() != null) {
                stmt.setDouble(6, acao.getEsforcoNecessario());
            } else {
                stmt.setNull(6, Types.NUMERIC);
            }

            stmt.setString(7, acao.getFrequenciaRecomendada());

            if (acao.getPontuacaoBase() != null) {
                stmt.setInt(8, acao.getPontuacaoBase());
            } else {
                stmt.setNull(8, Types.NUMERIC);
            }

            if (acao.getLimiteDiario() != null) {
                stmt.setInt(9, acao.getLimiteDiario());
            } else {
                stmt.setNull(9, Types.NUMERIC);
            }

            stmt.setString(10, acao.getStatus());
            stmt.setLong(11, acao.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException("Ação sustentável não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ação sustentável.", e);
        }
    }

    public void deletar(long id) {
        String sql = "DELETE FROM T_EV_ACAO_SUSTENTAVEIS WHERE id_acao = ?";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new EntidadeNaoEncontradaException("Ação sustentável não encontrada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ação sustentável.", e);
        }
    }
}