package com.example.myapplication.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ProductList {

    public Map<String, Product> products = new HashMap<>();

    public ProductList() {
    }


    public ProductList(Map<String, Product> products) {
        this.products = products;
    }
}
