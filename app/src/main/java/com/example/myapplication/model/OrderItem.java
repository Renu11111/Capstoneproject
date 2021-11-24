package com.example.myapplication.model;

public class OrderItem {
    public String emailid;
    public String name;
    public double price;
    public int quantity;

    public OrderItem(String emailid, String name, double price,int quantity) {
        this.emailid = emailid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem() {
    }
}

