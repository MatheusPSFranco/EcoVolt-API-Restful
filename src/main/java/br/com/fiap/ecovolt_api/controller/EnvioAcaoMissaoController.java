package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.EnvioAcaoMissaoDao;
import br.com.fiap.ecovolt_api.model.EnvioAcaoMissao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/envio-acao-missao")
public class EnvioAcaoMissaoController {

    private final EnvioAcaoMissaoDao dao;

    public EnvioAcaoMissaoController(EnvioAcaoMissaoDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<EnvioAcaoMissao> listar() {
        return dao.listar();
    }

    @GetMapping("/{idEnvio}/{idMissao}")
    public ResponseEntity<EnvioAcaoMissao> buscar(
            @PathVariable long idEnvio,
            @PathVariable long idMissao) {

        return ResponseEntity.ok(
                dao.buscar(idEnvio, idMissao)
        );
    }

    @PostMapping
    public ResponseEntity<EnvioAcaoMissao> inserir(
            @RequestBody EnvioAcaoMissao associacao) {

        EnvioAcaoMissao criada = dao.inserir(associacao);

        long idEnvio = criada.getEnvioAcao().getId();
        long idMissao = criada.getMissao().getId();

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idEnvio}/{idMissao}")
                .buildAndExpand(idEnvio, idMissao)
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(dao.buscar(idEnvio, idMissao));
    }

    @PutMapping("/{idEnvio}/{idMissao}")
    public ResponseEntity<EnvioAcaoMissao> atualizar(
            @PathVariable long idEnvio,
            @PathVariable long idMissao,
            @RequestBody EnvioAcaoMissao associacao) {

        dao.atualizar(idEnvio, idMissao, associacao);

        return ResponseEntity.ok(
                dao.buscar(idEnvio, idMissao)
        );
    }

    @DeleteMapping("/{idEnvio}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable long idEnvio,
            @PathVariable long idMissao) {

        dao.deletar(idEnvio, idMissao);

        return ResponseEntity.noContent().build();
    }
}