package com.example.user.Timer.presentation.ModelInPresentationLayer;

public class ModelInPresentationLayer {
    private long id;

    private long time;

    private String date;

    private long goal;

    public ModelInPresentationLayer() {
    }

    public ModelInPresentationLayer(long id, long time, String date,long goal) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.goal = goal;
    }

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
