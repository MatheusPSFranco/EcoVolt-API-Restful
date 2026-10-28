package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.PontuacaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoPontuacaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroPontuacaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesPontuacaoDto;
import br.com.fiap.ecovolt_api.model.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pontuacao")
public class PontuacaoController {

    private final PontuacaoDao dao;
    private final ModelMapper mapper;

    public PontuacaoController(PontuacaoDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesPontuacaoDto>> listar() {
        List<DetalhesPontuacaoDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesPontuacaoDto> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesPontuacaoDto> inserir(
            @Valid @RequestBody CadastroPontuacaoDto dto) {

        Pontuacao pontuacao = mapper.map(dto, Pontuacao.class);
        setFks(pontuacao, dto.getIdUsuario(), dto.getIdEnvioAcao(),
                dto.getIdValidacao(), dto.getIdRecursoRevisao(),
                dto.getIdMissao(), dto.getIdLoginDiario(),
                dto.getIdInteracao(), dto.getIdNivel());

        Pontuacao inserida = dao.inserir(pontuacao);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(inserida.getId()).toUri();

        return ResponseEntity.created(location).body(toDto(inserida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesPontuacaoDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoPontuacaoDto dto) {

        Pontuacao pontuacao = mapper.map(dto, Pontuacao.class);
        setFks(pontuacao, dto.getIdUsuario(), dto.getIdEnvioAcao(),
                dto.getIdValidacao(), dto.getIdRecursoRevisao(),
                dto.getIdMissao(), dto.getIdLoginDiario(),
                dto.getIdInteracao(), dto.getIdNivel());

        return ResponseEntity.ok(toDto(dao.atualizar(id, pontuacao)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private void setFks(Pontuacao p, Long idUsuario, Long idEnvioAcao,
                         Long idValidacao, Long idRecursoRevisao,
                         Long idMissao, Long idLoginDiario,
                         Long idInteracao, Long idNivel) {
        if (idUsuario != null) {
            Usuario u = new Usuario(); u.setId(idUsuario); p.setUsuario(u);
        }
        if (idEnvioAcao != null) {
            EnvioAcao e = new EnvioAcao(); e.setId(idEnvioAcao); p.setEnvioAcao(e);
        }
        if (idValidacao != null) {
            Validacao v = new Validacao(); v.setId(idValidacao); p.setValidacao(v);
        }
        if (idRecursoRevisao != null) {
            RecursoRevisao r = new RecursoRevisao(); r.setId(idRecursoRevisao); p.setRecursoRevisao(r);
        }
        if (idMissao != null) {
            Missao m = new Missao(); m.setId(idMissao); p.setMissao(m);
        }
        if (idLoginDiario != null) {
            LoginDiario l = new LoginDiario(); l.setId(idLoginDiario); p.setLoginDiario(l);
        }
        if (idInteracao != null) {
            Interacao i = new Interacao(); i.setId(idInteracao); p.setInteracao(i);
        }
        if (idNivel != null) {
            Nivel n = new Nivel(); n.setId(idNivel); p.setNivel(n);
        }
    }

    private DetalhesPontuacaoDto toDto(Pontuacao p) {
        DetalhesPontuacaoDto dto = mapper.map(p,
                DetalhesPontuacaoDto.class);
        if (p.getUsuario() != null) dto.setIdUsuario(p.getUsuario().getId());
        if (p.getEnvioAcao() != null) dto.setIdEnvioAcao(p.getEnvioAcao().getId());
        if (p.getValidacao() != null) dto.setIdValidacao(p.getValidacao().getId());
        if (p.getRecursoRevisao() != null) dto.setIdRecursoRevisao(p.getRecursoRevisao().getId());
        if (p.getMissao() != null) dto.setIdMissao(p.getMissao().getId());
        if (p.getLoginDiario() != null) dto.setIdLoginDiario(p.getLoginDiario().getId());
        if (p.getInteracao() != null) dto.setIdInteracao(p.getInteracao().getId());
        if (p.getNivel() != null) dto.setIdNivel(p.getNivel().getId());
        return dto;
    }
}
