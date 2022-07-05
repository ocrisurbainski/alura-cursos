package br.com.alura.forum.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginFormRequestDto {

    @Schema(name = "email", description = "Email para identificar o usuário.", example = "aluno@email.com")
    private String email;

    @Schema(name = "senha", description = "Senha para identificar o usuário.", example = "123456")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
