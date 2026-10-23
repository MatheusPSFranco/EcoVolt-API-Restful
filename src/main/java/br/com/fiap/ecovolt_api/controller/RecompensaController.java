package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RecompensaDao;
import br.com.fiap.ecovolt_api.model.Recompensa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recompensa")
public class RecompensaController {

    private final RecompensaDao recompensaDao;

    public RecompensaController(RecompensaDao recompensaDao) {
        this.recompensaDao = recompensaDao;
    }

    @GetMapping
    public ResponseEntity<List<Recompensa>> listar() {
        return ResponseEntity.ok(recompensaDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recompensa> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(recompensaDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Recompensa> inserir(
            @RequestBody Recompensa recompensa) {

        Recompensa recompensaInserida =
                recompensaDao.inserir(recompensa);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recompensaInserida.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(recompensaInserida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recompensa> atualizar(
            @PathVariable Long id,
            @RequestBody Recompensa recompensa) {

        return ResponseEntity.ok(
                recompensaDao.atualizar(id, recompensa)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        recompensaDao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}