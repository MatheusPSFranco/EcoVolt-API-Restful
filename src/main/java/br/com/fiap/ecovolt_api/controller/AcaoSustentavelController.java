package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.AcaoSustentavelDao;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/acao-sustentavel")
public class AcaoSustentavelController {

    private final AcaoSustentavelDao dao;

    public AcaoSustentavelController(AcaoSustentavelDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<AcaoSustentavel> listar() throws SQLException {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoSustentavel> buscar(@PathVariable Long id)
            throws SQLException {

        AcaoSustentavel acao = dao.buscar(id);

        return ResponseEntity.ok(acao);
    }

    @PostMapping
    public ResponseEntity<AcaoSustentavel> adicionar(
            @RequestBody AcaoSustentavel acao,
            UriComponentsBuilder uriBuilder) throws SQLException {

        dao.inserir(acao);

        URI uri = uriBuilder.path("/acao-sustentavel/{id}")
                .buildAndExpand(acao.getId())
                .toUri();

        return ResponseEntity.created(uri).body(acao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody AcaoSustentavel acao) throws SQLException {

        acao.setId(id);

        dao.atualizar(acao);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
            throws SQLException {

        dao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}