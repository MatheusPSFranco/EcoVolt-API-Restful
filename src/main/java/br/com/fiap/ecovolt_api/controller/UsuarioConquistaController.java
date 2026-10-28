package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.UsuarioConquistaDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoUsuarioConquistaDto;
import br.com.fiap.ecovolt_api.dto.CadastroUsuarioConquistaDto;
import br.com.fiap.ecovolt_api.dto.DetalhesUsuarioConquistaDto;
import br.com.fiap.ecovolt_api.model.Conquista;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.UsuarioConquista;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario-conquista")
public class UsuarioConquistaController {

    private final UsuarioConquistaDao dao;
    private final ModelMapper mapper;

    public UsuarioConquistaController(UsuarioConquistaDao dao,
                                      ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesUsuarioConquistaDto>> listar() {
        List<DetalhesUsuarioConquistaDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<DetalhesUsuarioConquistaDto> buscar(
            @PathVariable Long idUsuario,
            @PathVariable Long idConquista) {
        return ResponseEntity.ok(
                toDto(dao.buscar(idUsuario, idConquista)));
    }

    @PostMapping
    public ResponseEntity<DetalhesUsuarioConquistaDto> inserir(
            @Valid @RequestBody CadastroUsuarioConquistaDto dto) {

        UsuarioConquista uc = mapper.map(dto, UsuarioConquista.class);
        uc.setUsuario(criarUsuario(dto.getIdUsuario()));
        uc.setConquista(criarConquista(dto.getIdConquista()));

        dao.inserir(uc);

        UsuarioConquista criada = dao.buscar(
                dto.getIdUsuario(), dto.getIdConquista());

        URI location = URI.create("/usuario-conquista/"
                + dto.getIdUsuario() + "/" + dto.getIdConquista());

        return ResponseEntity.created(location).body(toDto(criada));
    }

    @PutMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<DetalhesUsuarioConquistaDto> atualizar(
            @PathVariable Long idUsuario,
            @PathVariable Long idConquista,
            @Valid @RequestBody AtualizacaoUsuarioConquistaDto dto) {

        UsuarioConquista uc = mapper.map(dto, UsuarioConquista.class);
        uc.setUsuario(criarUsuario(idUsuario));
        uc.setConquista(criarConquista(idConquista));

        dao.atualizar(idUsuario, idConquista, uc);

        return ResponseEntity.ok(
                toDto(dao.buscar(idUsuario, idConquista)));
    }

    @DeleteMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long idUsuario,
            @PathVariable Long idConquista) {
        dao.deletar(idUsuario, idConquista);
        return ResponseEntity.noContent().build();
    }

    private DetalhesUsuarioConquistaDto toDto(UsuarioConquista uc) {
        DetalhesUsuarioConquistaDto dto = mapper.map(uc,
                DetalhesUsuarioConquistaDto.class);
        if (uc.getUsuario() != null)
            dto.setIdUsuario(uc.getUsuario().getId());
        if (uc.getConquista() != null)
            dto.setIdConquista(uc.getConquista().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private Conquista criarConquista(Long id) {
        Conquista c = new Conquista();
        c.setId(id);
        return c;
    }
}
