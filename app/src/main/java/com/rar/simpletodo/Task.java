package com.rar.simpletodo;

public class Task {
    private int id;
    private String title;
    private String description;
    private int done;

    public Task(int id, String title, String description, int done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
