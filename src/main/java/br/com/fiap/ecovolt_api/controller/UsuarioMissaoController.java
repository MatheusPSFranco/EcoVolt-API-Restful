package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.UsuarioMissaoDao;
import br.com.fiap.ecovolt_api.model.UsuarioMissao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario-missao")
public class UsuarioMissaoController {

    private final UsuarioMissaoDao usuarioMissaoDao;

    public UsuarioMissaoController(UsuarioMissaoDao usuarioMissaoDao) {
        this.usuarioMissaoDao = usuarioMissaoDao;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioMissao>> listar() {
        return ResponseEntity.ok(usuarioMissaoDao.listar());
    }

    @GetMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<UsuarioMissao> buscar(
            @PathVariable Long idUsuario,
            @PathVariable Long idMissao) {

        return ResponseEntity.ok(
                usuarioMissaoDao.buscar(idUsuario, idMissao));
    }

    @PostMapping
    public ResponseEntity<UsuarioMissao> inserir(
            @RequestBody UsuarioMissao usuarioMissao) {

        usuarioMissaoDao.inserir(usuarioMissao);

        Long idUsuario = usuarioMissao.getUsuario().getId();
        Long idMissao = usuarioMissao.getMissao().getId();

        UsuarioMissao associacaoCriada =
                usuarioMissaoDao.buscar(idUsuario, idMissao);

        URI location = URI.create(
                "/usuario-missao/" + idUsuario + "/" + idMissao);

        return ResponseEntity.created(location).body(associacaoCriada);
    }

    @PutMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<UsuarioMissao> atualizar(
            @PathVariable Long idUsuario,
            @PathVariable Long idMissao,
            @RequestBody UsuarioMissao usuarioMissao) {

        usuarioMissaoDao.atualizar(
                idUsuario, idMissao, usuarioMissao);

        return ResponseEntity.ok(
                usuarioMissaoDao.buscar(idUsuario, idMissao));
    }

    @DeleteMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long idUsuario,
            @PathVariable Long idMissao) {

        usuarioMissaoDao.deletar(idUsuario, idMissao);

        return ResponseEntity.noContent().build();
    }
}