package com.app.myapplication.model;

import java.io.Serializable;

public class Category implements Serializable {
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    private String categoryId;

    public Category(String categoryId, String categoryName, int image) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.image = image;
}
    private String categoryName;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
