package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.AcaoSustentavelMissaoDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoAcaoSustentavelMissaoDto;
import br.com.fiap.ecovolt_api.dto.CadastroAcaoSustentavelMissaoDto;
import br.com.fiap.ecovolt_api.dto.DetalhesAcaoSustentavelMissaoDto;
import br.com.fiap.ecovolt_api.model.AcaoSustentavel;
import br.com.fiap.ecovolt_api.model.AcaoSustentavelMissao;
import br.com.fiap.ecovolt_api.model.Missao;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/acao-sustentavel-missao")
public class AcaoSustentavelMissaoController {

    private final AcaoSustentavelMissaoDao dao;
    private final ModelMapper mapper;

    public AcaoSustentavelMissaoController(AcaoSustentavelMissaoDao dao,
                                           ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DetalhesAcaoSustentavelMissaoDto> listar() {
        return dao.listar().stream()
                .map(this::toDto).toList();
    }

    @GetMapping("/{idAcao}/{idMissao}")
    public ResponseEntity<DetalhesAcaoSustentavelMissaoDto> buscar(
            @PathVariable long idAcao,
            @PathVariable long idMissao) {
        return ResponseEntity.ok(toDto(dao.buscar(idAcao, idMissao)));
    }

    @PostMapping
    public ResponseEntity<DetalhesAcaoSustentavelMissaoDto> inserir(
            @Valid @RequestBody CadastroAcaoSustentavelMissaoDto dto) {

        AcaoSustentavelMissao asm = mapper.map(dto,
                AcaoSustentavelMissao.class);
        asm.setAcaoSustentavel(criarAcao(dto.getIdAcaoSustentavel()));
        asm.setMissao(criarMissao(dto.getIdMissao()));

        dao.inserir(asm);

        URI location = URI.create("/acao-sustentavel-missao/"
                + dto.getIdAcaoSustentavel() + "/" + dto.getIdMissao());

        return ResponseEntity.created(location)
                .body(toDto(dao.buscar(
                        dto.getIdAcaoSustentavel(), dto.getIdMissao())));
    }

    @PutMapping("/{idAcao}/{idMissao}")
    public ResponseEntity<DetalhesAcaoSustentavelMissaoDto> atualizar(
            @PathVariable long idAcao,
            @PathVariable long idMissao,
            @Valid @RequestBody AtualizacaoAcaoSustentavelMissaoDto dto) {

        AcaoSustentavelMissao asm = mapper.map(dto,
                AcaoSustentavelMissao.class);
        asm.setAcaoSustentavel(criarAcao(idAcao));
        asm.setMissao(criarMissao(idMissao));

        dao.atualizar(idAcao, idMissao, asm);

        return ResponseEntity.ok(toDto(dao.buscar(idAcao, idMissao)));
    }

    @DeleteMapping("/{idAcao}/{idMissao}")
    public ResponseEntity<Void> deletar(
            @PathVariable long idAcao,
            @PathVariable long idMissao) {
        dao.deletar(idAcao, idMissao);
        return ResponseEntity.noContent().build();
    }

    private DetalhesAcaoSustentavelMissaoDto toDto(
            AcaoSustentavelMissao asm) {
        DetalhesAcaoSustentavelMissaoDto dto = mapper.map(asm,
                DetalhesAcaoSustentavelMissaoDto.class);
        if (asm.getAcaoSustentavel() != null)
            dto.setIdAcaoSustentavel(asm.getAcaoSustentavel().getId());
        if (asm.getMissao() != null)
            dto.setIdMissao(asm.getMissao().getId());
        return dto;
    }

    private AcaoSustentavel criarAcao(long id) {
        AcaoSustentavel a = new AcaoSustentavel();
        a.setId(id);
        return a;
    }

    private Missao criarMissao(long id) {
        Missao m = new Missao();
        m.setId(id);
        return m;
    }
}
