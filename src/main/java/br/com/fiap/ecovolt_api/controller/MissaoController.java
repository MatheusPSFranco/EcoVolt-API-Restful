package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.MissaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoMissaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroMissaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesMissaoDto;
import br.com.fiap.ecovolt_api.model.Missao;
import br.com.fiap.ecovolt_api.model.Nivel;
import br.com.fiap.ecovolt_api.model.Tier;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/missao")
public class MissaoController {

    private final MissaoDao dao;
    private final ModelMapper mapper;

    public MissaoController(MissaoDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesMissaoDto> listar() {
        return dao.listar().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesMissaoDto> buscar(@PathVariable long id) {
        Missao missao = dao.buscar(id);
        return ResponseEntity.ok(toDto(missao));
    }

    @PostMapping
    public ResponseEntity<DetalhesMissaoDto> inserir(
            @Valid @RequestBody CadastroMissaoDto dto,
            UriComponentsBuilder uriBuilder) {

        Missao missao = mapper.map(dto, Missao.class);
        missao.setTier(criarTier(dto.getIdTier()));
        missao.setNivel(criarNivel(dto.getIdNivel()));

        Missao inserida = dao.inserir(missao);

        URI uri = uriBuilder.path("/missao/{id}")
                .buildAndExpand(inserida.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(toDto(dao.buscar(inserida.getId())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesMissaoDto> atualizar(
            @PathVariable long id,
            @Valid @RequestBody AtualizacaoMissaoDto dto) {

        Missao missao = mapper.map(dto, Missao.class);
        missao.setId(id);
        missao.setTier(criarTier(dto.getIdTier()));
        missao.setNivel(criarNivel(dto.getIdNivel()));

        dao.atualizar(missao);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesMissaoDto toDto(Missao m) {
        DetalhesMissaoDto dto = mapper.map(m, DetalhesMissaoDto.class);
        if (m.getTier() != null) dto.setIdTier(m.getTier().getId());
        if (m.getNivel() != null) dto.setIdNivel(m.getNivel().getId());
        return dto;
    }

    private Tier criarTier(Long id) {
        if (id == null) return null;
        Tier tier = new Tier();
        tier.setId(id);
        return tier;
    }

    private Nivel criarNivel(Long id) {
        if (id == null) return null;
        Nivel nivel = new Nivel();
        nivel.setId(id);
        return nivel;
    }
}
