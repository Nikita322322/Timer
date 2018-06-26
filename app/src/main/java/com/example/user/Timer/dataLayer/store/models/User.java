package com.example.user.Timer.dataLayer.store.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.util.Log;
//import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.util.DiffUtil;

import static com.example.user.Timer.dataLayer.store.models.User.TABLE_NAME;

/**
 * Created by User on 17.05.2018.
 */

@Entity(tableName = TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "user";

    public User() {
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long time;

    private String date;

    private long goal;

    public long getGoal() {
        return goal;
    }

    public void setGoal(long goal) {
        this.goal = goal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static final DiffCallback<User> DIFF_CALLBACK = new DiffCallback<User>() {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };
}

