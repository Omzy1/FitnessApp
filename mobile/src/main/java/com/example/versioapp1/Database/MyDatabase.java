package com.example.versioapp1.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {
        User.class, Walking.class , Running.class
}, version = 4, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract WalkingDao walkingDao();
    public abstract RunningDao runningDao();

    public static MyDatabase Instance;

    public static MyDatabase getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (MyDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class,"database")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return Instance;

    }
}
