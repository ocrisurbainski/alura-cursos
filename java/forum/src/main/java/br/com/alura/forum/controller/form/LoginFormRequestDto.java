package br.com.alura.forum.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginFormRequestDto {

    @Email
    @NotBlank
    @Schema(name = "email", description = "Email para identificar o usuário.", example = "aluno@email.com")
    private String email;

    @Min(1)
    @NotNull
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
