package br.com.alura.forum.controller.impl;

import br.com.alura.forum.controller.TopicosController;
import br.com.alura.forum.controller.dto.DetalhesTopicoResponseDto;
import br.com.alura.forum.controller.dto.TopicoResponseDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoFormRequestDto;
import br.com.alura.forum.controller.form.CadastroTopicoFormRequestDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class TopicosControllerImpl implements TopicosController {

    private final TopicoRepository topicoRepository;

    private final CursoRepository cursoRepository;

    public TopicosControllerImpl(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    @GetMapping("/public/topicos")
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoResponseDto> lista(
            @RequestParam(required = false) String nomeCurso,
            @PageableDefault Pageable pageable) {
        Page<Topico> topicos;
        if (nomeCurso == null) {
            topicos = topicoRepository.findAll(pageable);
        } else {
            topicos = topicoRepository.findByCursoNome(nomeCurso, pageable);
        }
        return TopicoResponseDto.converter(topicos);
    }

    @Override
    @PostMapping("/topicos")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TopicoResponseDto> cadastrar(
            @RequestBody @Valid CadastroTopicoFormRequestDto form,
            UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponseDto(topico));
    }

    @Override
    @GetMapping("/public/topicos/{id}")
    public ResponseEntity<DetalhesTopicoResponseDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.map(value -> ResponseEntity.ok(new DetalhesTopicoResponseDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping("/topicos/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TopicoResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AtualizacaoTopicoFormRequestDto form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoResponseDto(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping("/topicos/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @PreAuthorize("hasRole('MODERADOR')")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}







