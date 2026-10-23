package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.MissaoDao;
import br.com.fiap.ecovolt_api.model.Missao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/missao")
public class MissaoController {

    private final MissaoDao dao;

    public MissaoController(MissaoDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Missao> listar() {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public Missao buscar(@PathVariable long id) {
        return dao.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Missao> inserir(
            @RequestBody Missao missao,
            UriComponentsBuilder uriBuilder) {

        Missao missaoInserida = dao.inserir(missao);

        URI uri = uriBuilder
                .path("/missao/{id}")
                .buildAndExpand(missaoInserida.getId())
                .toUri();

        Missao resposta = dao.buscar(missaoInserida.getId());

        return ResponseEntity.created(uri).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Missao> atualizar(
            @PathVariable long id,
            @RequestBody Missao missao) {

        missao.setId(id);

        dao.atualizar(missao);

        return ResponseEntity.ok(dao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}