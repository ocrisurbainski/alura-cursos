package br.com.alura.forum.controller.dto;

public class TokenDto {
    private final String token;
    private final String type = "Bearer";

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

}
