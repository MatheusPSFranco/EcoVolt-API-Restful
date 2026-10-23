package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.OdsDao;
import br.com.fiap.ecovolt_api.model.Ods;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ods")
public class OdsController {

    private final OdsDao dao;

    public OdsController(OdsDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Ods> listar() {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ods> buscar(@PathVariable long id) {
        return ResponseEntity.ok(dao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Ods> inserir(
            @RequestBody Ods ods,
            UriComponentsBuilder uriBuilder) {

        Ods odsInserida = dao.inserir(ods);

        URI uri = uriBuilder
                .path("/ods/{id}")
                .buildAndExpand(odsInserida.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(dao.buscar(odsInserida.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ods> atualizar(
            @PathVariable long id,
            @RequestBody Ods ods) {

        ods.setId(id);

        dao.atualizar(ods);

        return ResponseEntity.ok(dao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}