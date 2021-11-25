package com.app.myapplication.model;

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
