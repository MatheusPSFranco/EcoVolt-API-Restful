package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RankingDao;
import br.com.fiap.ecovolt_api.model.Ranking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingDao rankingDao;

    public RankingController(RankingDao rankingDao) {
        this.rankingDao = rankingDao;
    }

    @GetMapping
    public ResponseEntity<List<Ranking>> listar() {
        return ResponseEntity.ok(rankingDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranking> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(rankingDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Ranking> inserir(@RequestBody Ranking ranking) {
        rankingDao.inserir(ranking);

        Ranking rankingCriado = rankingDao.buscar(ranking.getId());

        return ResponseEntity
                .created(URI.create("/ranking/" + ranking.getId()))
                .body(rankingCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ranking> atualizar(
            @PathVariable Long id,
            @RequestBody Ranking ranking) {

        rankingDao.atualizar(id, ranking);

        return ResponseEntity.ok(rankingDao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        rankingDao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}