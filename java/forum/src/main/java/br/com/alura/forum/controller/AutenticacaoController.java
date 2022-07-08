package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TokenResponseDto;
import br.com.alura.forum.controller.form.LoginFormRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AutenticacaoController {
    
    @Operation(
            operationId = "autenticar",
            description = "Método para que o usuário se autentique na aplicação e obtenha acesso as funcionalidades protegidas da aplicação.")
    @ApiResponse(responseCode = "200", description = "Usuário autenticado na aplicação.")
    @ApiResponse(responseCode = "400", description = "Não foi possível autenticar o usuário.")
    ResponseEntity<TokenResponseDto> autenticar(LoginFormRequestDto form);

}
