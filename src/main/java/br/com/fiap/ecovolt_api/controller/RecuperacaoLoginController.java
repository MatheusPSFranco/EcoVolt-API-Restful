package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RecuperacaoLoginDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoRecuperacaoLoginDto;
import br.com.fiap.ecovolt_api.dto.CadastroRecuperacaoLoginDto;
import br.com.fiap.ecovolt_api.dto.DetalhesRecuperacaoLoginDto;
import br.com.fiap.ecovolt_api.model.LoginDiario;
import br.com.fiap.ecovolt_api.model.RecuperacaoLogin;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recuperacao-login")
public class RecuperacaoLoginController {

    private final RecuperacaoLoginDao dao;
    private final ModelMapper mapper;

    public RecuperacaoLoginController(RecuperacaoLoginDao dao,
                                      ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesRecuperacaoLoginDto>> listar() {
        List<DetalhesRecuperacaoLoginDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesRecuperacaoLoginDto> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesRecuperacaoLoginDto> inserir(
            @Valid @RequestBody CadastroRecuperacaoLoginDto dto) {

        RecuperacaoLogin rec = mapper.map(dto, RecuperacaoLogin.class);
        rec.setUsuario(criarUsuario(dto.getIdUsuario()));
        rec.setLogin(criarLogin(dto.getIdLogin()));

        RecuperacaoLogin inserida = dao.inserir(rec);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(inserida.getId()).toUri();

        return ResponseEntity.created(location).body(toDto(inserida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesRecuperacaoLoginDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoRecuperacaoLoginDto dto) {

        RecuperacaoLogin rec = mapper.map(dto, RecuperacaoLogin.class);
        rec.setUsuario(criarUsuario(dto.getIdUsuario()));
        rec.setLogin(criarLogin(dto.getIdLogin()));

        return ResponseEntity.ok(toDto(dao.atualizar(id, rec)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesRecuperacaoLoginDto toDto(RecuperacaoLogin r) {
        DetalhesRecuperacaoLoginDto dto = mapper.map(r,
                DetalhesRecuperacaoLoginDto.class);
        if (r.getUsuario() != null) dto.setIdUsuario(r.getUsuario().getId());
        if (r.getLogin() != null) dto.setIdLogin(r.getLogin().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private LoginDiario criarLogin(Long id) {
        if (id == null) return null;
        LoginDiario l = new LoginDiario();
        l.setId(id);
        return l;
    }
}
