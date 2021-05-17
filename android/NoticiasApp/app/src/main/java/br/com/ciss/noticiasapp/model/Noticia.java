package br.com.ciss.noticiasapp.model;

import java.util.Calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/09/20)
 */
@Entity(tableName = "noticia")
public class Noticia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "ds_titulo")
    private String dsTitulo;

    @ColumnInfo(name = "ds_conteudo")
    private String dsConteudo;

    @ColumnInfo(name = "dh_criacao")
    private Calendar dhCriacao;

    @ColumnInfo(name = "dh_alteracao")
    private Calendar dhAlteracao;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getDsTitulo() {

        return dsTitulo;
    }

    public void setDsTitulo(String dsTitulo) {

        this.dsTitulo = dsTitulo;
    }

    public String getDsConteudo() {

        return dsConteudo;
    }

    public void setDsConteudo(String dsConteudo) {

        this.dsConteudo = dsConteudo;
    }

    public Calendar getDhCriacao() {

        return dhCriacao;
    }

    public void setDhCriacao(Calendar dhCriacao) {

        this.dhCriacao = dhCriacao;
    }

    public Calendar getDhAlteracao() {

        return dhAlteracao;
    }

    public void setDhAlteracao(Calendar dhAlteracao) {

        this.dhAlteracao = dhAlteracao;
    }

}