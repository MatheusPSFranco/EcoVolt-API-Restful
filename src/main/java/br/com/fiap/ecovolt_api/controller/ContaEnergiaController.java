package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.ContaEnergiaDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoContaEnergiaDto;
import br.com.fiap.ecovolt_api.dto.CadastroContaEnergiaDto;
import br.com.fiap.ecovolt_api.dto.DetalhesContaEnergiaDto;
import br.com.fiap.ecovolt_api.model.ContaEnergia;
import br.com.fiap.ecovolt_api.model.Recompensa;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conta-energia")
public class ContaEnergiaController {

    private final ContaEnergiaDao dao;
    private final ModelMapper mapper;

    public ContaEnergiaController(ContaEnergiaDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesContaEnergiaDto>> listar() {
        List<DetalhesContaEnergiaDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesContaEnergiaDto> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesContaEnergiaDto> inserir(
            @Valid @RequestBody CadastroContaEnergiaDto dto) {

        ContaEnergia conta = mapper.map(dto, ContaEnergia.class);
        conta.setUsuario(criarUsuario(dto.getIdUsuario()));
        conta.setRecompensa(criarRecompensa(dto.getIdRecompensa()));

        ContaEnergia inserida = dao.inserir(conta);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(inserida.getId()).toUri();

        return ResponseEntity.created(location).body(toDto(inserida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesContaEnergiaDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoContaEnergiaDto dto) {

        ContaEnergia conta = mapper.map(dto, ContaEnergia.class);
        conta.setUsuario(criarUsuario(dto.getIdUsuario()));
        conta.setRecompensa(criarRecompensa(dto.getIdRecompensa()));

        return ResponseEntity.ok(toDto(dao.atualizar(id, conta)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesContaEnergiaDto toDto(ContaEnergia c) {
        DetalhesContaEnergiaDto dto = mapper.map(c,
                DetalhesContaEnergiaDto.class);
        if (c.getUsuario() != null) dto.setIdUsuario(c.getUsuario().getId());
        if (c.getRecompensa() != null)
            dto.setIdRecompensa(c.getRecompensa().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private Recompensa criarRecompensa(Long id) {
        if (id == null) return null;
        Recompensa r = new Recompensa();
        r.setId(id);
        return r;
    }
}
