package com.rar.simpletodo;

public class Task {
    private String title;
    private String description;
    private Boolean done;

    public Task (String title, String description)  {
        this.title = title;
        this.description = description;
        this.done = false;
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

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
