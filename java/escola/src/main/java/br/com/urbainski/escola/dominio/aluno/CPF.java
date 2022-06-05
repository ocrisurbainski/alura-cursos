package br.com.urbainski.escola.dominio.aluno;

import br.com.urbainski.escola.util.ValidationUtils;

public class CPF {

    private final String numero;

    public CPF(String numero) {
        if (ValidationUtils.isCpfFormatInvalid(numero)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

}
