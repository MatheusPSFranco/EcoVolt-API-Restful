package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RecursoRevisaoDao;
import br.com.fiap.ecovolt_api.model.RecursoRevisao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recurso-revisao")
public class RecursoRevisaoController {

    private final RecursoRevisaoDao recursoRevisaoDao;

    public RecursoRevisaoController(
            RecursoRevisaoDao recursoRevisaoDao) {
        this.recursoRevisaoDao = recursoRevisaoDao;
    }

    @GetMapping
    public ResponseEntity<List<RecursoRevisao>> listar() {
        return ResponseEntity.ok(recursoRevisaoDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoRevisao> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(recursoRevisaoDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<RecursoRevisao> inserir(
            @RequestBody RecursoRevisao recurso) {

        recursoRevisaoDao.inserir(recurso);

        RecursoRevisao recursoCriado =
                recursoRevisaoDao.buscar(recurso.getId());

        return ResponseEntity
                .created(URI.create("/recurso-revisao/" + recurso.getId()))
                .body(recursoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoRevisao> atualizar(
            @PathVariable Long id,
            @RequestBody RecursoRevisao recurso) {

        recursoRevisaoDao.atualizar(id, recurso);

        return ResponseEntity.ok(recursoRevisaoDao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recursoRevisaoDao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}