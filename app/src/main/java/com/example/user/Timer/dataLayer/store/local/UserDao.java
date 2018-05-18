package com.example.user.Timer.dataLayer.store.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.user.Timer.dataLayer.store.models.User;

import java.util.List;

/**
 * Created by User on 17.05.2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(User user);

    @Query("DELETE FROM user WHERE id = :userId")
    int deleteByUserId(long userId);
}
