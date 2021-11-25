package com.app.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Orders orders);

    @Query("SELECT * FROM ORDERS WHERE emailid=:emailID ORDER BY id desc")
    public List<Orders> findOrders(String emailID);

}
