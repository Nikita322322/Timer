package com.example.user.Timer.dataLayer.store.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.user.Timer.dataLayer.store.models.User;

/**
 * Created by User on 17.05.2018.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
