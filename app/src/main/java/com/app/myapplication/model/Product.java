package com.app.myapplication.model;

import java.io.Serializable;

public class Product implements Serializable {
    public String category_id;
    public String description;
    public String image_url;
    public String name;
    public double price;
    public double sale_price;

    public Product(String category_id, String description, String image_url, String name, double price, double sale_price) {
        this.category_id = category_id;
        this.description = description;
        this.image_url = image_url;
        this.name = name;
        this.price = price;
        this.sale_price = sale_price;
    }

    public Product() {
    }
}
