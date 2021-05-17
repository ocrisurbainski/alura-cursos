package br.com.ciss.noticiasapp.database.repository;

import java.util.List;

import br.com.ciss.noticiasapp.database.NoticiaDatabase;
import br.com.ciss.noticiasapp.database.dao.NoticiaDAO;
import br.com.ciss.noticiasapp.model.Noticia;

import android.content.Context;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/09/20)
 */
public class NoticiaRepository {

    private NoticiaDAO dao;

    public NoticiaRepository(Context context) {

        dao = NoticiaDatabase.getInstance(context).noticiaDAO();
    }

    public List<Noticia> findAll() {

        return dao.findAll();
    }

}
