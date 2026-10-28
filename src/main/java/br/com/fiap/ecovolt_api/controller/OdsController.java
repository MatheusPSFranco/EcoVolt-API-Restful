package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.OdsDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoOdsDto;
import br.com.fiap.ecovolt_api.dto.CadastroOdsDto;
import br.com.fiap.ecovolt_api.dto.DetalhesOdsDto;
import br.com.fiap.ecovolt_api.model.Ods;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ods")
public class OdsController {

    private final OdsDao dao;
    private final ModelMapper mapper;

    public OdsController(OdsDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesOdsDto> listar() {
        return dao.listar().stream()
                .map(o -> mapper.map(o, DetalhesOdsDto.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesOdsDto> buscar(@PathVariable long id) {
        Ods ods = dao.buscar(id);
        return ResponseEntity.ok(mapper.map(ods, DetalhesOdsDto.class));
    }

    @PostMapping
    public ResponseEntity<DetalhesOdsDto> inserir(
            @Valid @RequestBody CadastroOdsDto dto,
            UriComponentsBuilder uriBuilder) {

        Ods ods = mapper.map(dto, Ods.class);
        Ods odsInserida = dao.inserir(ods);

        URI uri = uriBuilder.path("/ods/{id}")
                .buildAndExpand(odsInserida.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(mapper.map(dao.buscar(odsInserida.getId()),
                        DetalhesOdsDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesOdsDto> atualizar(
            @PathVariable long id,
            @Valid @RequestBody AtualizacaoOdsDto dto) {

        Ods ods = mapper.map(dto, Ods.class);
        ods.setId(id);
        dao.atualizar(ods);

        return ResponseEntity.ok(
                mapper.map(dao.buscar(id), DetalhesOdsDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
