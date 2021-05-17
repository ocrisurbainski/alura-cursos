package br.com.alura.leilao.database.repository;

import java.util.List;

import br.com.alura.leilao.database.LeilaoDatabase;
import br.com.alura.leilao.database.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
public class UsuarioRepository {

    private final UsuarioDao dao;

    public UsuarioRepository(@NonNull Context context) {

        this.dao = LeilaoDatabase.getInstance(context).usuarioDao();
    }

    public UsuarioRepository(@NonNull LeilaoDatabase database) {

        this.dao = database.usuarioDao();
    }

    public Usuario insert(@NonNull Usuario usuario) {

        long id = dao.insert(usuario);
        return dao.findById(id);
    }

    public List<Usuario> findAll() {

        return dao.findAll();
    }

}