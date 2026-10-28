package br.com.fiap.ecovolt_api.controller;

import br.com.fiap.ecovolt_api.dao.RecompensaDao;
import br.com.fiap.ecovolt_api.dto.AtualizacaoRecompensaDto;
import br.com.fiap.ecovolt_api.dto.CadastroRecompensaDto;
import br.com.fiap.ecovolt_api.dto.DetalhesRecompensaDto;
import br.com.fiap.ecovolt_api.model.Ranking;
import br.com.fiap.ecovolt_api.model.Recompensa;
import br.com.fiap.ecovolt_api.model.Tier;
import br.com.fiap.ecovolt_api.model.Usuario;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recompensa")
public class RecompensaController {

    private final RecompensaDao dao;
    private final ModelMapper mapper;

    public RecompensaController(RecompensaDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DetalhesRecompensaDto>> listar() {
        List<DetalhesRecompensaDto> lista = dao.listar().stream()
                .map(this::toDto).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesRecompensaDto> buscar(
            @PathVariable Long id) {
        return ResponseEntity.ok(toDto(dao.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<DetalhesRecompensaDto> inserir(
            @Valid @RequestBody CadastroRecompensaDto dto) {

        Recompensa recompensa = mapper.map(dto, Recompensa.class);
        recompensa.setUsuario(criarUsuario(dto.getIdUsuario()));
        recompensa.setTier(criarTier(dto.getIdTier()));
        recompensa.setRanking(criarRanking(dto.getIdRanking()));

        Recompensa inserida = dao.inserir(recompensa);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(inserida.getId()).toUri();

        return ResponseEntity.created(location).body(toDto(inserida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalhesRecompensaDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoRecompensaDto dto) {

        Recompensa recompensa = mapper.map(dto, Recompensa.class);
        recompensa.setUsuario(criarUsuario(dto.getIdUsuario()));
        recompensa.setTier(criarTier(dto.getIdTier()));
        recompensa.setRanking(criarRanking(dto.getIdRanking()));

        return ResponseEntity.ok(toDto(dao.atualizar(id, recompensa)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dao.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DetalhesRecompensaDto toDto(Recompensa r) {
        DetalhesRecompensaDto dto = mapper.map(r,
                DetalhesRecompensaDto.class);
        if (r.getUsuario() != null) dto.setIdUsuario(r.getUsuario().getId());
        if (r.getTier() != null) dto.setIdTier(r.getTier().getId());
        if (r.getRanking() != null) dto.setIdRanking(r.getRanking().getId());
        return dto;
    }

    private Usuario criarUsuario(Long id) {
        if (id == null) return null;
        Usuario u = new Usuario();
        u.setId(id);
        return u;
    }

    private Tier criarTier(Long id) {
        if (id == null) return null;
        Tier t = new Tier();
        t.setId(id);
        return t;
    }

    private Ranking criarRanking(Long id) {
        if (id == null) return null;
        Ranking r = new Ranking();
        r.setId(id);
        return r;
    }
}
