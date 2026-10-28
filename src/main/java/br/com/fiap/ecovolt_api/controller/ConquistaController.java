package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.ConquistaDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoConquistaDto;
import br.com.fiap.ecovolt_api.dto.CadastroConquistaDto;
import br.com.fiap.ecovolt_api.dto.DetalhesConquistaDto;
import br.com.fiap.ecovolt_api.model.Conquista;
import br.com.fiap.ecovolt_api.model.Nivel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conquista")
public class ConquistaController {

    private final ConquistaDao dao;
    private final ModelMapper mapper;

    public ConquistaController(ConquistaDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesConquistaDto> listar() {
        return dao.listar().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesConquistaDto> buscar(@PathVariable long id) {
        Conquista conquista = dao.buscar(id);
        return ResponseEntity.ok(toDto(conquista));
    }

    @PostMapping
    public ResponseEntity<DetalhesConquistaDto> inserir(
            @Valid @RequestBody CadastroConquistaDto dto,
            UriComponentsBuilder uriBuilder) {

        Conquista conquista = mapper.map(dto, Conquista.class);
        conquista.setNivel(criarNivel(dto.getIdNivel()));

        Conquista criada = dao.inserir(conquista);

        URI uri = uriBuilder.path("/conquista/{id}")
                .buildAndExpand(criada.getId()).toUri();

        return ResponseEntity.created(uri).body(toDto(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesConquistaDto> atualizar(
            @PathVariable long id,
            @Valid @RequestBody AtualizacaoConquistaDto dto) {

        Conquista conquista = mapper.map(dto, Conquista.class);
        conquista.setId(id);
        conquista.setNivel(criarNivel(dto.getIdNivel()));

        dao.atualizar(conquista);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesConquistaDto toDto(Conquista c) {
        DetalhesConquistaDto dto = mapper.map(c, DetalhesConquistaDto.class);
        if (c.getNivel() != null) dto.setIdNivel(c.getNivel().getId());
        return dto;
    }

    private Nivel criarNivel(Long id) {
        if (id == null) return null;
        Nivel nivel = new Nivel();
        nivel.setId(id);
        return nivel;
    }
}
