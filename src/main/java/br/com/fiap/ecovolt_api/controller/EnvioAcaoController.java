package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.EnvioAcaoDao;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/envio-acao")
public class EnvioAcaoController {

    private final EnvioAcaoDao dao;

    public EnvioAcaoController(EnvioAcaoDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<EnvioAcao> listar() {
        return dao.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioAcao> buscar(
            @PathVariable long id) {
        return ResponseEntity.ok(dao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<EnvioAcao> inserir(
            @RequestBody EnvioAcao envio,
            UriComponentsBuilder uriBuilder) {

        EnvioAcao criado = dao.inserir(envio);

        URI uri = uriBuilder
                .path("/envio-acao/{id}")
                .buildAndExpand(criado.getId())
                .toUri();

        return ResponseEntity.created(uri).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioAcao> atualizar(
            @PathVariable long id,
            @RequestBody EnvioAcao envio) {

        dao.atualizar(id, envio);
        envio.setId(id);

        return ResponseEntity.ok(envio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}