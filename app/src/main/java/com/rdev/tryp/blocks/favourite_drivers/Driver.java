package com.rdev.tryp.blocks.favourite_drivers;

public class Driver {
    private String title;
    private String description;
    private boolean like;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Driver(String title, String description, boolean like) {
        this.title = title;
        this.description = description;
        this.like = like;
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
}
