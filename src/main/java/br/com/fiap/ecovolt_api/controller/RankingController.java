package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RankingDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoRankingDto;
import br.com.fiap.ecovolt_api.dto.CadastroRankingDto;
import br.com.fiap.ecovolt_api.dto.DetalhesRankingDto;
import br.com.fiap.ecovolt_api.model.Ranking;
import br.com.fiap.ecovolt_api.model.Tier;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingDao dao;
    private final ModelMapper mapper;

    public RankingController(RankingDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesRankingDto>> listar() {
        List<DetalhesRankingDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesRankingDto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesRankingDto> inserir(
            @Valid @RequestBody CadastroRankingDto dto) {

        Ranking ranking = mapper.map(dto, Ranking.class);
        ranking.setUsuario(criarUsuario(dto.getIdUsuario()));
        ranking.setTier(criarTier(dto.getIdTier()));

        dao.inserir(ranking);
        Ranking criado = dao.buscar(ranking.getId());

        return ResponseEntity
                .created(URI.create("/ranking/" + ranking.getId()))
                .body(toDto(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesRankingDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoRankingDto dto) {

        Ranking ranking = mapper.map(dto, Ranking.class);
        ranking.setUsuario(criarUsuario(dto.getIdUsuario()));
        ranking.setTier(criarTier(dto.getIdTier()));

        dao.atualizar(id, ranking);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesRankingDto toDto(Ranking r) {
        DetalhesRankingDto dto = mapper.map(r, DetalhesRankingDto.class);
        if (r.getUsuario() != null) dto.setIdUsuario(r.getUsuario().getId());
        if (r.getTier() != null) dto.setIdTier(r.getTier().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private Tier criarTier(Long id) {
        if (id == null) return null;
        Tier t = new Tier();
        t.setId(id);
        return t;
    }
}
