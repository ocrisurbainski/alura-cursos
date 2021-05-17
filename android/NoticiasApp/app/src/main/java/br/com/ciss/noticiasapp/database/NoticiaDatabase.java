package br.com.ciss.noticiasapp.database;

import br.com.ciss.noticiasapp.database.converters.CalendarConverter;
import br.com.ciss.noticiasapp.database.dao.NoticiaDAO;
import br.com.ciss.noticiasapp.model.Noticia;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/09/20)
 */
@Database(entities = {
        Noticia.class
}, version = 1, exportSchema = false)
@TypeConverters(CalendarConverter.class)
public abstract class NoticiaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "noticia.db";
    private static NoticiaDatabase instance;

    public abstract NoticiaDAO noticiaDAO();

    public static NoticiaDatabase getInstance(Context context) {

        if (instance == null) {

            instance = criarDatabase(context);
        }

        return instance;
    }

    private static synchronized NoticiaDatabase criarDatabase(Context context) {

        return Room.databaseBuilder(context, NoticiaDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

}