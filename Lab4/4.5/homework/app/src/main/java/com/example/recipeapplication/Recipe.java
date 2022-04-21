package com.example.recipeapplication;

import java.io.Serializable;

public class Recipe implements Serializable {
    String name;
    String description;
    String content;
    int imageId;

    public Recipe(String name, String description, String content, int imageId) {
        this.name = name;
        this.description = description;
        this.content = content;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
