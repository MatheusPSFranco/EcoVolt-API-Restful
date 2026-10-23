package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.ContaEnergiaDao;
import br.com.fiap.ecovolt_api.model.ContaEnergia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conta-energia")
public class ContaEnergiaController {

    private final ContaEnergiaDao contaEnergiaDao;

    public ContaEnergiaController(ContaEnergiaDao contaEnergiaDao) {
        this.contaEnergiaDao = contaEnergiaDao;
    }

    @GetMapping
    public ResponseEntity<List<ContaEnergia>> listar() {
        return ResponseEntity.ok(contaEnergiaDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaEnergia> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(contaEnergiaDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ContaEnergia> inserir(
            @RequestBody ContaEnergia contaEnergia) {

        ContaEnergia contaInserida =
                contaEnergiaDao.inserir(contaEnergia);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contaInserida.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(contaInserida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaEnergia> atualizar(
            @PathVariable Long id,
            @RequestBody ContaEnergia contaEnergia) {

        return ResponseEntity.ok(
                contaEnergiaDao.atualizar(id, contaEnergia)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaEnergiaDao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}