package com.example.user.Timer.dataLayer.store.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by User on 17.05.2018.
 */

@Entity(tableName = "user")
public class User {
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
}

