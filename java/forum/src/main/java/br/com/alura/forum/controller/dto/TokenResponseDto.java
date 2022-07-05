package br.com.alura.forum.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokenResponseDto {

    @Schema(name = "token", description = "Token gerado para o autenticação")
    private final String token;
    @Schema(name = "type", accessMode = Schema.AccessMode.READ_ONLY, example = "Bearer")
    private final String type = "Bearer";

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

}
