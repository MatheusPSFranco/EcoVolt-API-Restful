package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.LoginDiarioDao;
import br.com.fiap.ecovolt_api.model.LoginDiario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/login-diario")
public class LoginDiarioController {

    private final LoginDiarioDao loginDiarioDao;

    public LoginDiarioController(LoginDiarioDao loginDiarioDao) {
        this.loginDiarioDao = loginDiarioDao;
    }

    @GetMapping
    public ResponseEntity<List<LoginDiario>> listar() {
        return ResponseEntity.ok(loginDiarioDao.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginDiario> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(loginDiarioDao.buscar(id));
    }

    @PostMapping
    public ResponseEntity<LoginDiario> inserir(
            @RequestBody LoginDiario loginDiario) {

        loginDiarioDao.inserir(loginDiario);

        LoginDiario loginCriado =
                loginDiarioDao.buscar(loginDiario.getId());

        return ResponseEntity
                .created(URI.create("/login-diario/" + loginDiario.getId()))
                .body(loginCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginDiario> atualizar(
            @PathVariable Long id,
            @RequestBody LoginDiario loginDiario) {

        loginDiarioDao.atualizar(id, loginDiario);

        return ResponseEntity.ok(loginDiarioDao.buscar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        loginDiarioDao.deletar(id);

        return ResponseEntity.noContent().build();
    }
}