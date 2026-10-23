package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.ValidacaoDao;
import br.com.fiap.ecovolt_api.model.Validacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/validacao")
public class ValidacaoController {

    private final ValidacaoDao dao;

    public ValidacaoController(ValidacaoDao dao) {
        this.dao = dao;
    }

    // Lista todas as validações.
    @GetMapping
    public List<Validacao> listar() throws SQLException {
        return dao.listar();
    }

    // Busca uma validação pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Validacao> buscar(@PathVariable Long id)
            throws SQLException {

        Validacao validacao = dao.buscar(id);

        return ResponseEntity.ok(validacao);
    }

    // Cadastra uma nova validação.
    @PostMapping
    public ResponseEntity<Validacao> adicionar(
            @RequestBody Validacao validacao,
            UriComponentsBuilder uriBuilder
    ) throws SQLException {

        dao.inserir(validacao);

        Validacao validacaoCriada = dao.buscar(validacao.getId());

        URI uri = uriBuilder
                .path("/validacao/{id}")
                .buildAndExpand(validacaoCriada.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(validacaoCriada);
    }

    // Atualiza uma validação existente.
    @PutMapping("/{id}")
    public ResponseEntity<Validacao> atualizar(
            @PathVariable Long id,
            @RequestBody Validacao validacao
    ) throws SQLException {

        validacao.setId(id);

        dao.atualizar(validacao);

        Validacao validacaoAtualizada = dao.buscar(id);

        return ResponseEntity.ok(validacaoAtualizada);
    }

    // Exclui uma validação pelo ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {

        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}