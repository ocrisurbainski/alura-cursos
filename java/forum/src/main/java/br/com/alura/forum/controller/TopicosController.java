package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesTopicoResponseDto;
import br.com.alura.forum.controller.dto.TopicoResponseDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoFormRequestDto;
import br.com.alura.forum.controller.form.CadastroTopicoFormRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface TopicosController {

    @Operation(operationId = "lista", description = "Pesquisar os tópicos de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Sucesso ao fazer a pesquisa de tópicos.")
    Page<TopicoResponseDto> lista(
            @Parameter(name = "nomeCurso", description = "Nome do curso para pesquisar os tópicos.") String nomeCurso,
            @ParameterObject Pageable pageable);

    @Operation(operationId = "cadastrar", description = "Cadastrar novos tópicos no sistema.")
    @ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar novo tópico.")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar novo tópico.")
    ResponseEntity<TopicoResponseDto> cadastrar(CadastroTopicoFormRequestDto form, UriComponentsBuilder uriBuilder);

    @Operation(operationId = "detalhar", description = "Pesquisar um tópico pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Sucesso ao pesquisar o tópico.")
    @ApiResponse(responseCode = "404", description = "Não foi encontrado o tópico pesquisado.")
    ResponseEntity<DetalhesTopicoResponseDto> detalhar(
            @Parameter(name = "id", description = "Identificador do tópico a ser pesquisado.") Long id);

    @Operation(operationId = "atualizar", description = "Atualizar um tópicos no sistema.")
    @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar o tópico.")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar o tópico.")
    ResponseEntity<TopicoResponseDto> atualizar(
            @Parameter(name = "id", description = "Identificador do tópico a ser pesquisado.") Long id,
            AtualizacaoTopicoFormRequestDto form);

    @Operation(operationId = "remover", description = "Remover um tópicos no sistema.")
    @ApiResponse(responseCode = "200", description = "Sucesso ao remover o tópico.")
    @ApiResponse(responseCode = "404", description = "Quando o tópico a ser removido não é encontrado.")
    ResponseEntity<?> remover(
            @Parameter(name = "id", description = "Identificador do tópico a ser pesquisado.") Long id);

}







