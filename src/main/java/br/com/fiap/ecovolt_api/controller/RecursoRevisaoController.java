package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RecursoRevisaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoRecursoRevisaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroRecursoRevisaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesRecursoRevisaoDto;
import br.com.fiap.ecovolt_api.model.RecursoRevisao;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.Validacao;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recurso-revisao")
public class RecursoRevisaoController {

    private final RecursoRevisaoDao dao;
    private final ModelMapper mapper;

    public RecursoRevisaoController(RecursoRevisaoDao dao,
                                    ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesRecursoRevisaoDto>> listar() {
        List<DetalhesRecursoRevisaoDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesRecursoRevisaoDto> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesRecursoRevisaoDto> inserir(
            @Valid @RequestBody CadastroRecursoRevisaoDto dto) {

        RecursoRevisao recurso = mapper.map(dto, RecursoRevisao.class);
        recurso.setValidacao(criarValidacao(dto.getIdValidacao()));
        recurso.setUsuario(criarUsuario(dto.getIdUsuario()));
        recurso.setRevisor(criarUsuario(dto.getIdRevisor()));

        dao.inserir(recurso);
        RecursoRevisao criado = dao.buscar(recurso.getId());

        return ResponseEntity
                .created(URI.create("/recurso-revisao/" + recurso.getId()))
                .body(toDto(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesRecursoRevisaoDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoRecursoRevisaoDto dto) {

        RecursoRevisao recurso = mapper.map(dto, RecursoRevisao.class);
        recurso.setValidacao(criarValidacao(dto.getIdValidacao()));
        recurso.setUsuario(criarUsuario(dto.getIdUsuario()));
        recurso.setRevisor(criarUsuario(dto.getIdRevisor()));

        dao.atualizar(id, recurso);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesRecursoRevisaoDto toDto(RecursoRevisao r) {
        DetalhesRecursoRevisaoDto dto = mapper.map(r,
                DetalhesRecursoRevisaoDto.class);
        if (r.getValidacao() != null)
            dto.setIdValidacao(r.getValidacao().getId());
        if (r.getUsuario() != null)
            dto.setIdUsuario(r.getUsuario().getId());
        if (r.getRevisor() != null)
            dto.setIdRevisor(r.getRevisor().getId());
        return dto;
    }

    private Validacao criarValidacao(Long id) {
        if (id == null) return null;
        Validacao v = new Validacao();
        v.setId(id);
        return v;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }
}
