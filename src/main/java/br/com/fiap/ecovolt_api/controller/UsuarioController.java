package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.UsuarioDao;
import br.com.fiap.ecovolt_api.model.Usuario;
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

    public UsuarioController(UsuarioDao dao) {
        this.dao = dao;
    }

    // Lista todos os usuários.
    @GetMapping
    public List<Usuario> listar() throws SQLException {
        return dao.listar();
    }

    // Busca um usuário pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id)
            throws SQLException {

        Usuario usuario = dao.buscar(id);

        return ResponseEntity.ok(usuario);
    }

    // Cadastra um novo usuário.
    @PostMapping
    public ResponseEntity<Usuario> adicionar(
            @RequestBody Usuario usuario,
            UriComponentsBuilder uriBuilder
    ) throws SQLException {

        dao.inserir(usuario);

        Usuario usuarioCriado = dao.buscar(usuario.getId());

        URI uri = uriBuilder
                .path("/usuario/{id}")
                .buildAndExpand(usuarioCriado.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(usuarioCriado);
    }

    // Atualiza um usuário existente.
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario
    ) throws SQLException {

        usuario.setId(id);

        dao.atualizar(usuario);

        Usuario usuarioAtualizado = dao.buscar(id);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Exclui um usuário pelo ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {

        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}