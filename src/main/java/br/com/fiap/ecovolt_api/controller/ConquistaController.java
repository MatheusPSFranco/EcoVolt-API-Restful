package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.ConquistaDao;
import br.com.fiap.ecovolt_api.model.Conquista;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conquista")
public class ConquistaController {

    private final ConquistaDao dao;

    public ConquistaController(ConquistaDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Conquista> listar() {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public Conquista buscar(@PathVariable long id) {
        return dao.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Conquista> inserir(
            @RequestBody Conquista conquista,
            UriComponentsBuilder uriBuilder) {

        Conquista criada = dao.inserir(conquista);

        URI uri = uriBuilder
                .path("/conquista/{id}")
                .buildAndExpand(criada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conquista> atualizar(
            @PathVariable long id,
            @RequestBody Conquista conquista) {

        conquista.setId(id);
        dao.atualizar(conquista);

        return ResponseEntity.ok(conquista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}