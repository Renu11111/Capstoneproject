package com.example.myapplication.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Orders")
public class Orders {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String orderid;
    public String emailid;
    public String name;
    public double price;

    public Orders(String orderid, String emailid, String name, double price) {
        this.orderid = orderid;
        this.emailid = emailid;
        this.name = name;
        this.price = price;
    }

    @Ignore
    public Orders() {
    }

}