package com.app.myapplication.model;

import java.io.Serializable;
import java.util.Objects;

public class
CartItem implements Serializable {

    public String name;
    public double price;
    public int quantity;
    public double total_price;

    public CartItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total_price = price * quantity;
    }

    public CartItem() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return name.equals(cartItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
