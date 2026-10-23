package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.TierDao;
import br.com.fiap.ecovolt_api.model.Tier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tier")
public class TierController {

    private final TierDao dao;

    public TierController(TierDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Tier> listar() {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tier> buscar(@PathVariable long id) {
        return ResponseEntity.ok(dao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Tier> inserir(
            @RequestBody Tier tier,
            UriComponentsBuilder uriBuilder) {

        Tier tierInserido = dao.inserir(tier);

        URI uri = uriBuilder
                .path("/tier/{id}")
                .buildAndExpand(tierInserido.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(dao.buscar(tierInserido.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tier> atualizar(
            @PathVariable long id,
            @RequestBody Tier tier) {

        tier.setId(id);

        dao.atualizar(tier);

        return ResponseEntity.ok(dao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}