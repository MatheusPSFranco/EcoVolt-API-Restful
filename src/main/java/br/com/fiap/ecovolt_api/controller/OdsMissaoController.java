package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.OdsMissaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoOdsMissaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroOdsMissaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesOdsMissaoDto;
import br.com.fiap.ecovolt_api.model.Missao;
import br.com.fiap.ecovolt_api.model.Ods;
import br.com.fiap.ecovolt_api.model.OdsMissao;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ods-missao")
public class OdsMissaoController {

    private final OdsMissaoDao dao;
    private final ModelMapper mapper;

    public OdsMissaoController(OdsMissaoDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesOdsMissaoDto> listar() {
        return dao.listar().stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{idOds}/{idMissao}")
    public ResponseEntity<DetalhesOdsMissaoDto> buscar(
            @PathVariable long idOds,
            @PathVariable long idMissao) {
        return ResponseEntity.ok(toDto(dao.buscar(idOds, idMissao)));
    }

    @PostMapping
    public ResponseEntity<DetalhesOdsMissaoDto> inserir(
            @Valid @RequestBody CadastroOdsMissaoDto dto,
            UriComponentsBuilder uriBuilder) {

        OdsMissao om = mapper.map(dto, OdsMissao.class);
        om.setOds(criarOds(dto.getIdOds()));
        om.setMissao(criarMissao(dto.getIdMissao()));

        dao.inserir(om);

        URI uri = uriBuilder.path("/ods-missao/{idOds}/{idMissao}")
                .buildAndExpand(dto.getIdOds(), dto.getIdMissao())
                .toUri();

        return ResponseEntity.created(uri)
                .body(toDto(dao.buscar(dto.getIdOds(), dto.getIdMissao())));
    }

    @PutMapping("/{idOds}/{idMissao}")
    public ResponseEntity<DetalhesOdsMissaoDto> atualizar(
            @PathVariable long idOds,
            @PathVariable long idMissao,
            @Valid @RequestBody AtualizacaoOdsMissaoDto dto) {

        OdsMissao om = mapper.map(dto, OdsMissao.class);
        om.setOds(criarOds(idOds));
        om.setMissao(criarMissao(idMissao));

        dao.atualizar(idOds, idMissao, om);

        return ResponseEntity.ok(toDto(dao.buscar(idOds, idMissao)));
    }

    @DeleteMapping("/{idOds}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable long idOds,
            @PathVariable long idMissao) {
        dao.deletar(idOds, idMissao);
        return ResponseEntity.noContent().build();
    }

    private DetalhesOdsMissaoDto toDto(OdsMissao om) {
        DetalhesOdsMissaoDto dto = mapper.map(om,
                DetalhesOdsMissaoDto.class);
        if (om.getOds() != null) dto.setIdOds(om.getOds().getId());
        if (om.getMissao() != null) dto.setIdMissao(om.getMissao().getId());
        return dto;
    }

    private Ods criarOds(long id) {
        Ods o = new Ods();
        o.setId(id);
        return o;
    }

    private Missao criarMissao(long id) {
        Missao m = new Missao();
        m.setId(id);
        return m;
    }
}
