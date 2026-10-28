package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.NivelDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoNivelDto;
import br.com.fiap.ecovolt_api.dto.CadastroNivelDto;
import br.com.fiap.ecovolt_api.dto.DetalhesNivelDto;
import br.com.fiap.ecovolt_api.model.Nivel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/nivel")
public class NivelController {

    private final NivelDao dao;
    private final ModelMapper mapper;

    public NivelController(NivelDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesNivelDto> listar() {
        return dao.listar().stream()
                .map(n -> mapper.map(n, DetalhesNivelDto.class))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesNivelDto> buscar(@PathVariable long id) {
        Nivel nivel = dao.buscar(id);
        return ResponseEntity.ok(mapper.map(nivel, DetalhesNivelDto.class));
    }

    @PostMapping
    public ResponseEntity<DetalhesNivelDto> inserir(
            @Valid @RequestBody CadastroNivelDto dto,
            UriComponentsBuilder uriBuilder) {

        Nivel nivel = mapper.map(dto, Nivel.class);
        Nivel nivelInserido = dao.inserir(nivel);

        URI uri = uriBuilder.path("/nivel/{id}")
                .buildAndExpand(nivelInserido.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(mapper.map(dao.buscar(nivelInserido.getId()),
                        DetalhesNivelDto.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesNivelDto> atualizar(
            @PathVariable long id,
            @Valid @RequestBody AtualizacaoNivelDto dto) {

        Nivel nivel = mapper.map(dto, Nivel.class);
        nivel.setId(id);
        dao.atualizar(nivel);

        return ResponseEntity.ok(
                mapper.map(dao.buscar(id), DetalhesNivelDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
