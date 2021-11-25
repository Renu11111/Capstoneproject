package com.app.myapplication.model;
import java.util.List;
import java.util.Map;

public class OrderModel {

    public Map<String, List<OrderItem>> items;

    public OrderModel() {
    }

    public OrderModel(Map<String, List<OrderItem>> items) {
        this.items = items;
    }
}
