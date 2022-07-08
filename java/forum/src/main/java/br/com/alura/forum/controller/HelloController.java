package br.com.alura.forum.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface HelloController {

    @Operation(
            operationId = "hello",
            description = "Método de exemplo para dizer olá ao usuário")
    @ApiResponse(responseCode = "200", description = "Sucesso na execução do método")
    String hello();

}
