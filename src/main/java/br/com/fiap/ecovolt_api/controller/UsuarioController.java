package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.UsuarioDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoUsuarioDto;
import br.com.fiap.ecovolt_api.dto.CadastroUsuarioDto;
import br.com.fiap.ecovolt_api.dto.DetalhesUsuarioDto;
import br.com.fiap.ecovolt_api.model.Nivel;
import br.com.fiap.ecovolt_api.model.Tier;
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
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioDao dao;
    private final ModelMapper mapper;

    public UsuarioController(UsuarioDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesUsuarioDto> listar() throws SQLException {
        return dao.listar().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesUsuarioDto> buscar(@PathVariable Long id)
            throws SQLException {
        Usuario usuario = dao.buscar(id);
        return ResponseEntity.ok(toDto(usuario));
    }

    @PostMapping
    public ResponseEntity<DetalhesUsuarioDto> adicionar(
            @Valid @RequestBody CadastroUsuarioDto dto,
            UriComponentsBuilder uriBuilder) throws SQLException {

        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setTier(criarTier(dto.getIdTier()));
        usuario.setNivel(criarNivel(dto.getIdNivel()));

        dao.inserir(usuario);
        Usuario usuarioCriado = dao.buscar(usuario.getId());

        URI uri = uriBuilder.path("/usuario/{id}")
                .buildAndExpand(usuarioCriado.getId()).toUri();

        return ResponseEntity.created(uri).body(toDto(usuarioCriado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesUsuarioDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoUsuarioDto dto)
            throws SQLException {

        Usuario usuario = mapper.map(dto, Usuario.class);
        usuario.setId(id);
        usuario.setTier(criarTier(dto.getIdTier()));
        usuario.setNivel(criarNivel(dto.getIdNivel()));

        dao.atualizar(usuario);
        Usuario usuarioAtualizado = dao.buscar(id);

        return ResponseEntity.ok(toDto(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesUsuarioDto toDto(Usuario u) {
        DetalhesUsuarioDto dto = mapper.map(u, DetalhesUsuarioDto.class);
        if (u.getTier() != null) dto.setIdTier(u.getTier().getId());
        if (u.getNivel() != null) dto.setIdNivel(u.getNivel().getId());
        return dto;
    }

    private Tier criarTier(Long id) {
        if (id == null) return null;
        Tier tier = new Tier();
        tier.setId(id);
        return tier;
    }

    private Nivel criarNivel(Long id) {
        if (id == null) return null;
        Nivel nivel = new Nivel();
        nivel.setId(id);
        return nivel;
    }
}
