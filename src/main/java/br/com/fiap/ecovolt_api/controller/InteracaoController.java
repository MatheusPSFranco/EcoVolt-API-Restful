package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.InteracaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoInteracaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroInteracaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesInteracaoDto;
import br.com.fiap.ecovolt_api.model.Interacao;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/interacao")
public class InteracaoController {

    private final InteracaoDao dao;
    private final ModelMapper mapper;

    public InteracaoController(InteracaoDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesInteracaoDto> listar() throws SQLException {
        return dao.listar().stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesInteracaoDto> buscar(
            @PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesInteracaoDto> adicionar(
            @Valid @RequestBody CadastroInteracaoDto dto,
            UriComponentsBuilder uriBuilder) throws SQLException {

        Interacao interacao = mapper.map(dto, Interacao.class);
        interacao.setUsuario(criarUsuario(dto.getIdUsuario()));

        dao.inserir(interacao);
        Interacao criada = dao.buscar(interacao.getId());

        URI uri = uriBuilder.path("/interacao/{id}")
                .buildAndExpand(criada.getId()).toUri();

        return ResponseEntity.created(uri).body(toDto(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesInteracaoDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoInteracaoDto dto)
            throws SQLException {

        Interacao interacao = mapper.map(dto, Interacao.class);
        interacao.setId(id);
        interacao.setUsuario(criarUsuario(dto.getIdUsuario()));

        dao.atualizar(interacao);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesInteracaoDto toDto(Interacao i) {
        DetalhesInteracaoDto dto = mapper.map(i,
                DetalhesInteracaoDto.class);
        if (i.getUsuario() != null) dto.setIdUsuario(i.getUsuario().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }
}
