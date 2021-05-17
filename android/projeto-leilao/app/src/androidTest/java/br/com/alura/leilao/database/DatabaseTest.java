package br.com.alura.leilao.database;

import org.junit.Before;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase.Callback;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

/**
 * @author Cristian Urbainski
 * @since 1.0 (25/09/20)
 */
public abstract class DatabaseTest {

    protected LeilaoDatabase database;

    @Before
    public void initDatabase() {

        Context context = InstrumentationRegistry.getContext();
        database = Room.inMemoryDatabaseBuilder(context, LeilaoDatabase.class).addCallback(getCallback()).build();
    }

    @NonNull
    public Callback getCallback() {

        return null;
    }

}