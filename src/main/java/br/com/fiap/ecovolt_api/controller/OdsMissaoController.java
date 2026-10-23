package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.OdsMissaoDao;
import br.com.fiap.ecovolt_api.model.OdsMissao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ods-missao")
public class OdsMissaoController {

    private final OdsMissaoDao dao;

    public OdsMissaoController(OdsMissaoDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<OdsMissao> listar() {
        return dao.listar();
    }

    @GetMapping("/{idOds}/{idMissao}")
    public OdsMissao buscar(
            @PathVariable long idOds,
            @PathVariable long idMissao) {

        return dao.buscar(idOds, idMissao);
    }

    @PostMapping
    public ResponseEntity<OdsMissao> inserir(
            @RequestBody OdsMissao associacao,
            UriComponentsBuilder uriBuilder) {

        OdsMissao associacaoInserida = dao.inserir(associacao);

        Long idOds = associacaoInserida.getOds().getId();
        Long idMissao = associacaoInserida.getMissao().getId();

        URI uri = uriBuilder
                .path("/ods-missao/{idOds}/{idMissao}")
                .buildAndExpand(idOds, idMissao)
                .toUri();

        OdsMissao resposta = dao.buscar(idOds, idMissao);

        return ResponseEntity.created(uri).body(resposta);
    }

    @PutMapping("/{idOds}/{idMissao}")
    public ResponseEntity<OdsMissao> atualizar(
            @PathVariable long idOds,
            @PathVariable long idMissao,
            @RequestBody OdsMissao associacao) {

        dao.atualizar(idOds, idMissao, associacao);

        return ResponseEntity.ok(
                dao.buscar(idOds, idMissao)
        );
    }

    @DeleteMapping("/{idOds}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable long idOds,
            @PathVariable long idMissao) {

        dao.deletar(idOds, idMissao);

        return ResponseEntity.noContent().build();
    }
}