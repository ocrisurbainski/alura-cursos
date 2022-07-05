package br.com.alura.forum.controller;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenResponseDto;
import br.com.alura.forum.controller.form.LoginFormRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(
            operationId = "autenticar",
            description = "Método para que o usuário se autentique na aplicação e obtenha acesso as funcionalidades protegidas da aplicação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado na aplicação."),
                    @ApiResponse(responseCode = "400", description = "Não foi possível autenticar o usuário."),
            })
    public ResponseEntity<TokenResponseDto> autenticar(@RequestBody @Valid LoginFormRequestDto form) {
        UsernamePasswordAuthenticationToken authenticationToken = form.converter();
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenResponseDto(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
