package br.com.urbainski.escola;

import br.com.urbainski.escola.util.ValidationUtils;

public class Telefone {

    private final String ddd;
    private final String numero;

    public Telefone(String ddd, String numero) {
        if (ValidationUtils.isDDDInvalid(ddd)) {
            throw new IllegalArgumentException("DDD inválido");
        }
        if (ValidationUtils.isNumeroTelefoneInvalid(numero)) {
            throw new IllegalArgumentException("Número telefone inválido");
        }
        this.ddd = ddd;
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumero() {
        return numero;
    }

}
