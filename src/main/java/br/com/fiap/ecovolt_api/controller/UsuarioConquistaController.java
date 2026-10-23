package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.UsuarioConquistaDao;
import br.com.fiap.ecovolt_api.model.UsuarioConquista;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario-conquista")
public class UsuarioConquistaController {

    private final UsuarioConquistaDao usuarioConquistaDao;

    public UsuarioConquistaController(
            UsuarioConquistaDao usuarioConquistaDao) {
        this.usuarioConquistaDao = usuarioConquistaDao;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioConquista>> listar() {
        return ResponseEntity.ok(usuarioConquistaDao.listar());
    }

    @GetMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<UsuarioConquista> buscar(
            @PathVariable Long idUsuario,
            @PathVariable Long idConquista) {

        return ResponseEntity.ok(
                usuarioConquistaDao.buscar(idUsuario, idConquista));
    }

    @PostMapping
    public ResponseEntity<UsuarioConquista> inserir(
            @RequestBody UsuarioConquista usuarioConquista) {

        usuarioConquistaDao.inserir(usuarioConquista);

        Long idUsuario = usuarioConquista.getUsuario().getId();
        Long idConquista = usuarioConquista.getConquista().getId();

        UsuarioConquista associacaoCriada =
                usuarioConquistaDao.buscar(idUsuario, idConquista);

        URI location = URI.create(
                "/usuario-conquista/" + idUsuario + "/" + idConquista);

        return ResponseEntity.created(location).body(associacaoCriada);
    }

    @PutMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<UsuarioConquista> atualizar(
            @PathVariable Long idUsuario,
            @PathVariable Long idConquista,
            @RequestBody UsuarioConquista usuarioConquista) {

        usuarioConquistaDao.atualizar(
                idUsuario, idConquista, usuarioConquista);

        return ResponseEntity.ok(
                usuarioConquistaDao.buscar(idUsuario, idConquista));
    }

    @DeleteMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long idUsuario,
            @PathVariable Long idConquista) {

        usuarioConquistaDao.deletar(idUsuario, idConquista);

        return ResponseEntity.noContent().build();
    }
}