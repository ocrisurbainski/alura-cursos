package br.com.alura.leilao.model;

import java.io.Serializable;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Usuario implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final long id;
    @NonNull
    @ColumnInfo(name = "nome")
    private final String nome;

    public Usuario(long id, String nome) {

        this.id = id;
        this.nome = nome;
    }

    @Ignore
    public Usuario(String nome) {

        this.id = 0L;
        this.nome = nome;
    }

    public long getId() {

        return id;
    }

    public String getNome() {

        return nome;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Usuario usuario = (Usuario) o;

        if (id != usuario.id) {
            return false;
        }
        return nome.equals(usuario.nome);
    }

    @Override
    public int hashCode() {

        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + nome.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return "(" + id + ") " + nome;
    }

}