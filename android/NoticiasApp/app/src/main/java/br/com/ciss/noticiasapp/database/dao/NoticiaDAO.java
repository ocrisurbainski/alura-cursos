package br.com.ciss.noticiasapp.database.dao;

import java.util.List;

import br.com.ciss.noticiasapp.model.Noticia;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/09/20)
 */
@Dao
public interface NoticiaDAO {

    @Query("select * from noticia")
    List<Noticia> findAll();

}