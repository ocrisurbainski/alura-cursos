package br.com.alura.leilao.database;

import br.com.alura.leilao.database.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class LeilaoDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "leilao.db";
    private static final Object object = new Object();
    private static LeilaoDatabase INSTANCE;

    public abstract UsuarioDao usuarioDao();

    public static LeilaoDatabase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (object) {

                INSTANCE = Room.databaseBuilder(context, LeilaoDatabase.class, DATABASE_NAME).build();
            }
        }

        return INSTANCE;
    }

}