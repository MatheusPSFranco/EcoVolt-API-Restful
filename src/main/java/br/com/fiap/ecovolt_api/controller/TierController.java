package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.TierDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoTierDto;
import br.com.fiap.ecovolt_api.dto.CadastroTierDto;
import br.com.fiap.ecovolt_api.dto.DetalhesTierDto;
import br.com.fiap.ecovolt_api.model.Tier;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tier")
public class TierController {

    private final TierDao dao;
    private final ModelMapper mapper;

    public TierController(TierDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesTierDto> listar() {
        return dao.listar().stream()
                .map(t -> mapper.map(t, DetalhesTierDto.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTierDto> buscar(@PathVariable long id) {
        Tier tier = dao.buscar(id);
        return ResponseEntity.ok(mapper.map(tier, DetalhesTierDto.class));
    }

    @PostMapping
    public ResponseEntity<DetalhesTierDto> inserir(
            @Valid @RequestBody CadastroTierDto dto,
            UriComponentsBuilder uriBuilder) {

        Tier tier = mapper.map(dto, Tier.class);
        Tier tierInserido = dao.inserir(tier);

        URI uri = uriBuilder.path("/tier/{id}")
                .buildAndExpand(tierInserido.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(mapper.map(dao.buscar(tierInserido.getId()),
                        DetalhesTierDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesTierDto> atualizar(
            @PathVariable long id,
            @Valid @RequestBody AtualizacaoTierDto dto) {

        Tier tier = mapper.map(dto, Tier.class);
        tier.setId(id);
        dao.atualizar(tier);

        return ResponseEntity.ok(
                mapper.map(dao.buscar(id), DetalhesTierDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
