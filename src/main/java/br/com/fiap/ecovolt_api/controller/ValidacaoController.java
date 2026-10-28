package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.ValidacaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoValidacaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroValidacaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesValidacaoDto;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import br.com.fiap.ecovolt_api.model.Validacao;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/validacao")
public class ValidacaoController {

    private final ValidacaoDao dao;
    private final ModelMapper mapper;

    public ValidacaoController(ValidacaoDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesValidacaoDto> listar() throws SQLException {
        return dao.listar().stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesValidacaoDto> buscar(
            @PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesValidacaoDto> adicionar(
            @Valid @RequestBody CadastroValidacaoDto dto,
            UriComponentsBuilder uriBuilder) throws SQLException {

        Validacao validacao = mapper.map(dto, Validacao.class);
        validacao.setEnvioAcao(criarEnvioAcao(dto.getIdEnvioAcao()));

        dao.inserir(validacao);
        Validacao criada = dao.buscar(validacao.getId());

        URI uri = uriBuilder.path("/validacao/{id}")
                .buildAndExpand(criada.getId()).toUri();

        return ResponseEntity.created(uri).body(toDto(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesValidacaoDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoValidacaoDto dto)
            throws SQLException {

        Validacao validacao = mapper.map(dto, Validacao.class);
        validacao.setId(id);
        validacao.setEnvioAcao(criarEnvioAcao(dto.getIdEnvioAcao()));

        dao.atualizar(validacao);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesValidacaoDto toDto(Validacao v) {
        DetalhesValidacaoDto dto = mapper.map(v,
                DetalhesValidacaoDto.class);
        if (v.getEnvioAcao() != null)
            dto.setIdEnvioAcao(v.getEnvioAcao().getId());
        return dto;
    }

    private EnvioAcao criarEnvioAcao(Long id) {
        if (id == null) return null;
        EnvioAcao e = new EnvioAcao();
        e.setId(id);
        return e;
    }
}
