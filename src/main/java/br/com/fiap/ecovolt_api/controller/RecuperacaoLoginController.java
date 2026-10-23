package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RecuperacaoLoginDao;
import br.com.fiap.ecovolt_api.model.RecuperacaoLogin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recuperacao-login")
public class RecuperacaoLoginController {

    private final RecuperacaoLoginDao recuperacaoLoginDao;

    public RecuperacaoLoginController(
            RecuperacaoLoginDao recuperacaoLoginDao) {
        this.recuperacaoLoginDao = recuperacaoLoginDao;
    }

    @GetMapping
    public ResponseEntity<List<RecuperacaoLogin>> listar() {
        return ResponseEntity.ok(recuperacaoLoginDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecuperacaoLogin> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(recuperacaoLoginDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<RecuperacaoLogin> inserir(
            @RequestBody RecuperacaoLogin recuperacao) {

        RecuperacaoLogin recuperacaoInserida =
                recuperacaoLoginDao.inserir(recuperacao);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recuperacaoInserida.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(recuperacaoInserida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecuperacaoLogin> atualizar(
            @PathVariable Long id,
            @RequestBody RecuperacaoLogin recuperacao) {

        return ResponseEntity.ok(
                recuperacaoLoginDao.atualizar(id, recuperacao)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recuperacaoLoginDao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}