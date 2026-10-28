package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.EnvioAcaoMissaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoEnvioAcaoMissaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroEnvioAcaoMissaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesEnvioAcaoMissaoDto;
import br.com.fiap.ecovolt_api.model.EnvioAcao;
import br.com.fiap.ecovolt_api.model.EnvioAcaoMissao;
import br.com.fiap.ecovolt_api.model.Missao;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/envio-acao-missao")
public class EnvioAcaoMissaoController {

    private final EnvioAcaoMissaoDao dao;
    private final ModelMapper mapper;

    public EnvioAcaoMissaoController(EnvioAcaoMissaoDao dao,
                                     ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesEnvioAcaoMissaoDto> listar() {
        return dao.listar().stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{idEnvio}/{idMissao}")
    public ResponseEntity<DetalhesEnvioAcaoMissaoDto> buscar(
            @PathVariable long idEnvio,
            @PathVariable long idMissao) {
        return ResponseEntity.ok(
                toDto(dao.buscar(idEnvio, idMissao)));
    }

    @PostMapping
    public ResponseEntity<DetalhesEnvioAcaoMissaoDto> inserir(
            @Valid @RequestBody CadastroEnvioAcaoMissaoDto dto) {

        EnvioAcaoMissao eam = mapper.map(dto, EnvioAcaoMissao.class);
        eam.setEnvioAcao(criarEnvioAcao(dto.getIdEnvioAcao()));
        eam.setMissao(criarMissao(dto.getIdMissao()));

        EnvioAcaoMissao criada = dao.inserir(eam);

        long idEnvio = dto.getIdEnvioAcao();
        long idMissao = dto.getIdMissao();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idEnvio}/{idMissao}")
                .buildAndExpand(idEnvio, idMissao).toUri();

        return ResponseEntity.created(uri)
                .body(toDto(dao.buscar(idEnvio, idMissao)));
    }

    @PutMapping("/{idEnvio}/{idMissao}")
    public ResponseEntity<DetalhesEnvioAcaoMissaoDto> atualizar(
            @PathVariable long idEnvio,
            @PathVariable long idMissao,
            @Valid @RequestBody AtualizacaoEnvioAcaoMissaoDto dto) {

        EnvioAcaoMissao eam = mapper.map(dto, EnvioAcaoMissao.class);
        eam.setEnvioAcao(criarEnvioAcao(idEnvio));
        eam.setMissao(criarMissao(idMissao));

        dao.atualizar(idEnvio, idMissao, eam);

        return ResponseEntity.ok(
                toDto(dao.buscar(idEnvio, idMissao)));
    }

    @DeleteMapping("/{idEnvio}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable long idEnvio,
            @PathVariable long idMissao) {
        dao.deletar(idEnvio, idMissao);
        return ResponseEntity.noContent().build();
    }

    private DetalhesEnvioAcaoMissaoDto toDto(EnvioAcaoMissao eam) {
        DetalhesEnvioAcaoMissaoDto dto = mapper.map(eam,
                DetalhesEnvioAcaoMissaoDto.class);
        if (eam.getEnvioAcao() != null)
            dto.setIdEnvioAcao(eam.getEnvioAcao().getId());
        if (eam.getMissao() != null)
            dto.setIdMissao(eam.getMissao().getId());
        return dto;
    }

    private EnvioAcao criarEnvioAcao(long id) {
        EnvioAcao e = new EnvioAcao();
        e.setId(id);
        return e;
    }

    private Missao criarMissao(long id) {
        Missao m = new Missao();
        m.setId(id);
        return m;
    }
}
