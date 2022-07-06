package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesTopicoResponseDto;
import br.com.alura.forum.controller.dto.TopicoResponseDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoFormRequestDto;
import br.com.alura.forum.controller.form.CadastroTopicoFormRequestDto;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class TopicosController {

    private final TopicoRepository topicoRepository;

    private final CursoRepository cursoRepository;

    public TopicosController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping("/public/topicos")
    @Cacheable(value = "listaDeTopicos")
    @Operation(
            operationId = "lista",
            description = "Pesquisar os tópicos de forma paginada.",
            responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso ao fazer a pesquisa de tópicos.")
            })
    public Page<TopicoResponseDto> lista(
            @Parameter(name = "nomeCurso", description = "Nome do curso para pesquisar os tópicos.") @RequestParam(required = false) String nomeCurso,
            @PageableDefault Pageable pageable) {
        Page<Topico> topicos;
        if (nomeCurso == null) {
            topicos = topicoRepository.findAll(pageable);
        } else {
            topicos = topicoRepository.findByCursoNome(nomeCurso, pageable);
        }
        return TopicoResponseDto.converter(topicos);
    }

    @PostMapping("/topicos")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @Operation(
            operationId = "cadastrar",
            description = "Cadastrar novos tópicos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar novo tópico."),
                    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar novo tópico.")
            })
    public ResponseEntity<TopicoResponseDto> cadastrar(
            @RequestBody @Valid CadastroTopicoFormRequestDto form,
            UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponseDto(topico));
    }

    @GetMapping("/public/topicos/{id}")
    @Operation(
            operationId = "detalhar",
            description = "Pesquisar um tópico pelo seu identificador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso ao pesquisar o tópico."),
                    @ApiResponse(responseCode = "404", description = "Não foi encontrado o tópico pesquisado.")
            })
    public ResponseEntity<DetalhesTopicoResponseDto> detalhar(
            @Parameter(name = "id", description = "Identificador do tópico a ser pesquisado.") @PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.map(value -> ResponseEntity.ok(new DetalhesTopicoResponseDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/topicos/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @Operation(
            operationId = "atualizar",
            description = "Atualizar um tópicos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar o tópico."),
                    @ApiResponse(responseCode = "400", description = "Erro ao atualizar o tópico.")
            })
    public ResponseEntity<TopicoResponseDto> atualizar(
            @Parameter(name = "id", description = "Identificador do tópico a ser pesquisado.") @PathVariable Long id,
            @RequestBody @Valid AtualizacaoTopicoFormRequestDto form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoResponseDto(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/topicos/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    @Operation(
            operationId = "remover",
            description = "Remover um tópicos no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso ao remover o tópico."),
                    @ApiResponse(responseCode = "404", description = "Quando o tópico a ser removido não é encontrado.")
            })
    public ResponseEntity<?> remover(
            @Parameter(name = "id", description = "Identificador do tópico a ser pesquisado.") @PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}







