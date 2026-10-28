package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.UsuarioMissaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoUsuarioMissaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroUsuarioMissaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesUsuarioMissaoDto;
import br.com.fiap.ecovolt_api.model.Missao;
import br.com.fiap.ecovolt_api.model.Usuario;
import br.com.fiap.ecovolt_api.model.UsuarioMissao;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario-missao")
public class UsuarioMissaoController {

    private final UsuarioMissaoDao dao;
    private final ModelMapper mapper;

    public UsuarioMissaoController(UsuarioMissaoDao dao,
                                   ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesUsuarioMissaoDto>> listar() {
        List<DetalhesUsuarioMissaoDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<DetalhesUsuarioMissaoDto> buscar(
            @PathVariable Long idUsuario,
            @PathVariable Long idMissao) {
        return ResponseEntity.ok(
                toDto(dao.buscar(idUsuario, idMissao)));
    }

    @PostMapping
    public ResponseEntity<DetalhesUsuarioMissaoDto> inserir(
            @Valid @RequestBody CadastroUsuarioMissaoDto dto) {

        UsuarioMissao um = mapper.map(dto, UsuarioMissao.class);
        um.setUsuario(criarUsuario(dto.getIdUsuario()));
        um.setMissao(criarMissao(dto.getIdMissao()));

        dao.inserir(um);

        UsuarioMissao criada = dao.buscar(
                dto.getIdUsuario(), dto.getIdMissao());

        URI location = URI.create("/usuario-missao/"
                + dto.getIdUsuario() + "/" + dto.getIdMissao());

        return ResponseEntity.created(location).body(toDto(criada));
    }

    @PutMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<DetalhesUsuarioMissaoDto> atualizar(
            @PathVariable Long idUsuario,
            @PathVariable Long idMissao,
            @Valid @RequestBody AtualizacaoUsuarioMissaoDto dto) {

        UsuarioMissao um = mapper.map(dto, UsuarioMissao.class);
        um.setUsuario(criarUsuario(idUsuario));
        um.setMissao(criarMissao(idMissao));

        dao.atualizar(idUsuario, idMissao, um);

        return ResponseEntity.ok(
                toDto(dao.buscar(idUsuario, idMissao)));
    }

    @DeleteMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long idUsuario,
            @PathVariable Long idMissao) {
        dao.deletar(idUsuario, idMissao);
        return ResponseEntity.noContent().build();
    }

    private DetalhesUsuarioMissaoDto toDto(UsuarioMissao um) {
        DetalhesUsuarioMissaoDto dto = mapper.map(um,
                DetalhesUsuarioMissaoDto.class);
        if (um.getUsuario() != null)
            dto.setIdUsuario(um.getUsuario().getId());
        if (um.getMissao() != null)
            dto.setIdMissao(um.getMissao().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private Missao criarMissao(Long id) {
        Missao m = new Missao();
        m.setId(id);
        return m;
    }
}
