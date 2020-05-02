package com.rar.simpletodo;

public class Task {
    private int id;
    private String title;
    private String description;
    private int done;

    Task(int id, String title, String description, int done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    int getDone() {
        return done;
    }

    void setDone(int done) {
        this.done = done;
    }
}
