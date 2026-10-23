package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.InteracaoDao;
import br.com.fiap.ecovolt_api.model.Interacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/interacao")
public class InteracaoController {

    private final InteracaoDao dao;

    public InteracaoController(InteracaoDao dao) {
        this.dao = dao;
    }

    // Lista todas as interações.
    @GetMapping
    public List<Interacao> listar() throws SQLException {
        return dao.listar();
    }

    // Busca uma interação pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Interacao> buscar(@PathVariable Long id)
            throws SQLException {

        Interacao interacao = dao.buscar(id);

        return ResponseEntity.ok(interacao);
    }

    // Cadastra uma nova interação.
    @PostMapping
    public ResponseEntity<Interacao> adicionar(
            @RequestBody Interacao interacao,
            UriComponentsBuilder uriBuilder
    ) throws SQLException {

        dao.inserir(interacao);

        Interacao interacaoCriada = dao.buscar(interacao.getId());

        URI uri = uriBuilder
                .path("/interacao/{id}")
                .buildAndExpand(interacaoCriada.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(interacaoCriada);
    }

    // Atualiza uma interação existente.
    @PutMapping("/{id}")
    public ResponseEntity<Interacao> atualizar(
            @PathVariable Long id,
            @RequestBody Interacao interacao
    ) throws SQLException {

        interacao.setId(id);

        dao.atualizar(interacao);

        Interacao interacaoAtualizada = dao.buscar(id);

        return ResponseEntity.ok(interacaoAtualizada);
    }

    // Exclui uma interação pelo ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {

        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}