package br.com.alura.leilao.database.dao;

import java.util.List;

import br.com.alura.leilao.model.Usuario;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Usuario usuario);

    @Query("select * from usuario where id = :id")
    Usuario findById(long id);

    @Query("select * from usuario")
    List<Usuario> findAll();

}