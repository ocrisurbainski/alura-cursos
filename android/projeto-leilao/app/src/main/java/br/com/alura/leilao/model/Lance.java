package br.com.alura.leilao.model;

import java.io.Serializable;

import br.com.alura.leilao.util.MoedaUtil;

import android.support.annotation.NonNull;

public class Lance implements Serializable, Comparable<Lance> {

    private final long id;
    private final Double valor;
    private final Usuario usuario;

    public Lance(long id, Usuario usuario, Double valor) {

        this.id = id;
        this.usuario = usuario;
        this.valor = valor;
    }

    public Lance(Usuario usuario, Double valor) {

        this.id = 0L;
        this.usuario = usuario;
        this.valor = valor;
    }

    public long getId() {

        return id;
    }

    public Double getValor() {

        return valor;
    }

    public String getValorFormatado() {

        return MoedaUtil.format(valor);
    }

    public Usuario getUsuario() {

        return usuario;
    }

    @Override
    public int compareTo(@NonNull Lance lance) {

        return lance.getValor().compareTo(getValor());
    }

}