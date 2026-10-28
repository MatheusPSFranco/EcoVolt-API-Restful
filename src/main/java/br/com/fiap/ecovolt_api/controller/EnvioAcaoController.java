package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.EnvioAcaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoEnvioAcaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroEnvioAcaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesEnvioAcaoDto;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/envio-acao")
public class EnvioAcaoController {

    private final EnvioAcaoDao dao;
    private final ModelMapper mapper;

    public EnvioAcaoController(EnvioAcaoDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesEnvioAcaoDto> listar() {
        return dao.listar().stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesEnvioAcaoDto> buscar(
            @PathVariable long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesEnvioAcaoDto> inserir(
            @Valid @RequestBody CadastroEnvioAcaoDto dto,
            UriComponentsBuilder uriBuilder) {

        EnvioAcao envio = mapper.map(dto, EnvioAcao.class);
        envio.setUsuario(criarUsuario(dto.getIdUsuario()));
        envio.setAcaoSustentavel(criarAcao(dto.getIdAcaoSustentavel()));

        EnvioAcao criado = dao.inserir(envio);

        URI uri = uriBuilder.path("/envio-acao/{id}")
                .buildAndExpand(criado.getId()).toUri();

        return ResponseEntity.created(uri).body(toDto(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesEnvioAcaoDto> atualizar(
            @PathVariable long id,
            @Valid @RequestBody AtualizacaoEnvioAcaoDto dto) {

        EnvioAcao envio = mapper.map(dto, EnvioAcao.class);
        envio.setUsuario(criarUsuario(dto.getIdUsuario()));
        envio.setAcaoSustentavel(criarAcao(dto.getIdAcaoSustentavel()));

        dao.atualizar(id, envio);
        envio.setId(id);

        return ResponseEntity.ok(toDto(envio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesEnvioAcaoDto toDto(EnvioAcao e) {
        DetalhesEnvioAcaoDto dto = mapper.map(e,
                DetalhesEnvioAcaoDto.class);
        if (e.getUsuario() != null) dto.setIdUsuario(e.getUsuario().getId());
        if (e.getAcaoSustentavel() != null)
            dto.setIdAcaoSustentavel(e.getAcaoSustentavel().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private AcaoSustentavel criarAcao(Long id) {
        if (id == null) return null;
        AcaoSustentavel a = new AcaoSustentavel();
        a.setId(id);
        return a;
    }
}
