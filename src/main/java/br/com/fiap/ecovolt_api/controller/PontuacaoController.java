package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.PontuacaoDao;
import br.com.fiap.ecovolt_api.model.Pontuacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pontuacao")
public class PontuacaoController {

    private final PontuacaoDao pontuacaoDao;

    public PontuacaoController(PontuacaoDao pontuacaoDao) {
        this.pontuacaoDao = pontuacaoDao;
    }

    @GetMapping
    public ResponseEntity<List<Pontuacao>> listar() {
        return ResponseEntity.ok(pontuacaoDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pontuacao> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(pontuacaoDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Pontuacao> inserir(
            @RequestBody Pontuacao pontuacao) {

        Pontuacao pontuacaoInserida =
                pontuacaoDao.inserir(pontuacao);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pontuacaoInserida.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(pontuacaoInserida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pontuacao> atualizar(
            @PathVariable Long id,
            @RequestBody Pontuacao pontuacao) {

        return ResponseEntity.ok(
                pontuacaoDao.atualizar(id, pontuacao)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pontuacaoDao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}