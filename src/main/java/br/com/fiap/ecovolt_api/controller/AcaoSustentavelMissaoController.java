package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.AcaoSustentavelMissaoDao;
import br.com.fiap.ecovolt_api.model.AcaoSustentavelMissao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acao-sustentavel-missao")
public class AcaoSustentavelMissaoController {

    private final AcaoSustentavelMissaoDao dao;

    public AcaoSustentavelMissaoController(
            AcaoSustentavelMissaoDao dao) {
        this.dao = dao;
    }

    // GET: listar todas as associações
    @GetMapping
    public List<AcaoSustentavelMissao> listar() {
        return dao.listar();
    }

    // GET: buscar associação pelos IDs da ação e da missão
    @GetMapping("/{idAcao}/{idMissao}")
    public AcaoSustentavelMissao buscar(
            @PathVariable long idAcao,
            @PathVariable long idMissao) {
        return dao.buscar(idAcao, idMissao);
    }

    // POST: inserir uma associação
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AcaoSustentavelMissao inserir(
            @RequestBody AcaoSustentavelMissao associacao) {
        return dao.inserir(associacao);
    }

    // PUT: atualizar uma associação existente
    @PutMapping("/{idAcao}/{idMissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(
            @PathVariable long idAcao,
            @PathVariable long idMissao,
            @RequestBody AcaoSustentavelMissao associacao) {
        dao.atualizar(idAcao, idMissao, associacao);
    }

    // DELETE: excluir uma associação
    @DeleteMapping("/{idAcao}/{idMissao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @PathVariable long idAcao,
            @PathVariable long idMissao) {
        dao.deletar(idAcao, idMissao);
    }
}