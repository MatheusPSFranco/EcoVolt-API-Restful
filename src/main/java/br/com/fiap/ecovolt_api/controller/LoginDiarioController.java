package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.LoginDiarioDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoLoginDiarioDto;
import br.com.fiap.ecovolt_api.dto.CadastroLoginDiarioDto;
import br.com.fiap.ecovolt_api.dto.DetalhesLoginDiarioDto;
import br.com.fiap.ecovolt_api.model.LoginDiario;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/login-diario")
public class LoginDiarioController {

    private final LoginDiarioDao dao;
    private final ModelMapper mapper;

    public LoginDiarioController(LoginDiarioDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesLoginDiarioDto>> listar() {
        List<DetalhesLoginDiarioDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesLoginDiarioDto> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesLoginDiarioDto> inserir(
            @Valid @RequestBody CadastroLoginDiarioDto dto) {

        LoginDiario login = mapper.map(dto, LoginDiario.class);
        login.setUsuario(criarUsuario(dto.getIdUsuario()));

        dao.inserir(login);
        LoginDiario criado = dao.buscar(login.getId());

        return ResponseEntity
                .created(URI.create("/login-diario/" + login.getId()))
                .body(toDto(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesLoginDiarioDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoLoginDiarioDto dto) {

        LoginDiario login = mapper.map(dto, LoginDiario.class);
        login.setUsuario(criarUsuario(dto.getIdUsuario()));

        dao.atualizar(id, login);

        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesLoginDiarioDto toDto(LoginDiario l) {
        DetalhesLoginDiarioDto dto = mapper.map(l,
                DetalhesLoginDiarioDto.class);
        if (l.getUsuario() != null) dto.setIdUsuario(l.getUsuario().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }
}
