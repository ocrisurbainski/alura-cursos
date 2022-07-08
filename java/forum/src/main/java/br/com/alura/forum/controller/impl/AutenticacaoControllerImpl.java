package br.com.alura.forum.controller.impl;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.AutenticacaoController;
import br.com.alura.forum.controller.dto.TokenResponseDto;
import br.com.alura.forum.controller.form.LoginFormRequestDto;
import org.springframework.context.annotation.Profile;
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
@Profile("remote")
public class AutenticacaoControllerImpl implements AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AutenticacaoControllerImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    @PostMapping
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
