package br.com.urbainski.escola.academico.dominio.aluno;

import br.com.urbainski.escola.util.ValidationUtils;

public class Email {

    private final String endereco;

    public Email(String endereco) {
        if (ValidationUtils.isEmailInvalid(endereco)) {
            throw new IllegalArgumentException("Endereço de email inválido");
        }
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

}
