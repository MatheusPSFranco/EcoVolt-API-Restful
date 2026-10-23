package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.NivelDao;
import br.com.fiap.ecovolt_api.model.Nivel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/nivel")
public class NivelController {

    private final NivelDao dao;

    public NivelController(NivelDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Nivel> listar() {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nivel> buscar(@PathVariable long id) {
        return ResponseEntity.ok(dao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Nivel> inserir(
            @RequestBody Nivel nivel,
            UriComponentsBuilder uriBuilder) {

        Nivel nivelInserido = dao.inserir(nivel);

        URI uri = uriBuilder
                .path("/nivel/{id}")
                .buildAndExpand(nivelInserido.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(dao.buscar(nivelInserido.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nivel> atualizar(
            @PathVariable long id,
            @RequestBody Nivel nivel) {

        nivel.setId(id);

        dao.atualizar(nivel);

        return ResponseEntity.ok(dao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}