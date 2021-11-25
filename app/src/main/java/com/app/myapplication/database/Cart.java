package com.app.myapplication.database;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Carts")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String emailid;
    public String name;
    public double price;
    public boolean status;

    public Cart( String emailid, String name, double price) {
        this.emailid = emailid;
        this.name = name;
        this.price = price;
        this.status = false;
    }

    @Ignore
    public Cart() {
    }
}
