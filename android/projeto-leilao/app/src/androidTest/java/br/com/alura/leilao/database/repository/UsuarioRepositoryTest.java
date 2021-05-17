package br.com.alura.leilao.database.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.alura.leilao.database.DatabaseTest;
import br.com.alura.leilao.model.Usuario;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase.Callback;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

/**
 * @author Cristian Urbainski
 * @since 1.0 (25/09/20)
 */
@RunWith(AndroidJUnit4.class)
public class UsuarioRepositoryTest extends DatabaseTest {

    private UsuarioRepository usuarioRepository;

    @Before
    public void setUp() {

        usuarioRepository = new UsuarioRepository(database);
    }

    @Test
    public void deve_inserirUsuario_quandoEnviadoUsuarioParaSalvar() {

        Usuario usuario = new Usuario("User 1");

        usuario = usuarioRepository.insert(usuario);

        Assert.assertEquals(5L, usuario.getId());
    }

    @Test
    public void deve_pesquisarUsuarios_quandoChamadoPesquisa() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        Assert.assertNotNull(usuarios);
        Assert.assertFalse(usuarios.isEmpty());
        Assert.assertEquals(4, usuarios.size());
    }

    @NonNull
    @Override
    public Callback getCallback() {

        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);

                final String sql = "insert into usuario (nome) values (?)";

                db.execSQL(sql, new Object[] { "Cristian" });
                db.execSQL(sql, new Object[] { "Joeli" });
                db.execSQL(sql, new Object[] { "Felipe" });
                db.execSQL(sql, new Object[] { "Fernanda" });
            }
        };
    }
}