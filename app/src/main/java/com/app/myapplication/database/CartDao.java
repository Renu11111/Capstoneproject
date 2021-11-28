package com.app.myapplication.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;


@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Cart cart);

    @Query("SELECT * FROM CARTS WHERE emailid=:emailID")
    public List<Cart> findCart(String emailID);

    @Delete
    public void deleteOrder(Cart orders);
}
