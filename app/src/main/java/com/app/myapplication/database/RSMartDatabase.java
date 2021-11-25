package com.app.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Cart.class, Orders.class}, version = 1, exportSchema = false)
public abstract class RSMartDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
    public abstract OrdersDao ordersDao();
    private static volatile RSMartDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RSMartDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RSMartDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RSMartDatabase.class, "rsmart_db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

