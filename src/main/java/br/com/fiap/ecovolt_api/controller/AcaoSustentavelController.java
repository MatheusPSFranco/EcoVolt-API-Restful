package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.AcaoSustentavelDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoAcaoSustentavelDto;
import br.com.fiap.ecovolt_api.dto.CadastroAcaoSustentavelDto;
import br.com.fiap.ecovolt_api.dto.DetalhesAcaoSustentavelDto;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import br.com.fiap.ecovolt_api.model.Ods;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/acao-sustentavel")
public class AcaoSustentavelController {

    private final AcaoSustentavelDao dao;
    private final ModelMapper mapper;

    public AcaoSustentavelController(AcaoSustentavelDao dao,
                                     ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesAcaoSustentavelDto> listar() throws SQLException {
        return dao.listar().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesAcaoSustentavelDto> buscar(
            @PathVariable Long id) throws SQLException {
        AcaoSustentavel acao = dao.buscar(id);
        return ResponseEntity.ok(toDto(acao));
    }

    @PostMapping
    public ResponseEntity<DetalhesAcaoSustentavelDto> adicionar(
            @Valid @RequestBody CadastroAcaoSustentavelDto dto,
            UriComponentsBuilder uriBuilder) throws SQLException {

        AcaoSustentavel acao = mapper.map(dto, AcaoSustentavel.class);
        acao.setOds(criarOds(dto.getIdOds()));

        dao.inserir(acao);

        URI uri = uriBuilder.path("/acao-sustentavel/{id}")
                .buildAndExpand(acao.getId()).toUri();

        return ResponseEntity.created(uri).body(toDto(acao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoAcaoSustentavelDto dto)
            throws SQLException {

        AcaoSustentavel acao = mapper.map(dto, AcaoSustentavel.class);
        acao.setId(id);
        acao.setOds(criarOds(dto.getIdOds()));

        dao.atualizar(acao);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesAcaoSustentavelDto toDto(AcaoSustentavel a) {
        DetalhesAcaoSustentavelDto dto = mapper.map(a,
                DetalhesAcaoSustentavelDto.class);
        if (a.getOds() != null) dto.setIdOds(a.getOds().getId());
        return dto;
    }

    private Ods criarOds(Long id) {
        if (id == null) return null;
        Ods ods = new Ods();
        ods.setId(id);
        return ods;
    }
}
