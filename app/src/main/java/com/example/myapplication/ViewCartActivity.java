package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.adapter.CartItemListAdapter;
import com.example.myapplication.database.Cart;
import com.example.myapplication.database.CartDao;
import com.example.myapplication.database.OrdersDao;
import com.example.myapplication.database.RSMartDatabase;

public class ViewCartActivity extends AppCompatActivity {
    private RSMartDatabase rsMartDatabase;
    private CartDao cartDao;
    private OrdersDao ordersDao;
    RecyclerView recyclerView;
    CartItemListAdapter adapter;
    List<CartItem> cartItemList;
    List<Cart> carts;
    private RecyclerView.LayoutManager layoutManager;
    Button btn_place;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        // Configure Database API
        rsMartDatabase = RSMartDatabase.getDatabase(getApplicationContext());
        cartDao = rsMartDatabase.cartDao();
        ordersDao = rsMartDatabase.ordersDao();
        cartItemList = getActiveCartItems();

        recyclerView = findViewById(R.id.recylerView);
        btn_place = findViewById(R.id.btn_place);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartItemListAdapter(this, cartItemList);
        recyclerView.setAdapter(adapter);
        btn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrders();
            }
        });
        if(cartItemList!=null && cartItemList.size()>0){
            double total = 0;
            for(CartItem item : cartItemList){
                total += item.total_price;
            }
            btn_place.setText("Place Order ($ " + total + ")");
        }
    }

    public List<CartItem> getActiveCartItems(){
        List<CartItem> items = new ArrayList<>();
        try{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            email = user.getEmail();
            carts = cartDao.findCart(email);
            if(carts!=null && carts.size()>0){
                for(Cart cart : carts){
                    int index = -1;
                    for(int i = 0; i < items.size(); i++){
                        if(items.get(i).name.equals(cart.name)){
                            index = i;
                            break;
                        }
                    }
                    if(index!=-1){
                        items.get(index).quantity += 1;
                    }else{
                        items.add(new CartItem(cart.name,cart.price,1));
                    }
                }
            }else{
                Toast.makeText(this, "No Item in Cart...", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){}
        return items;
    }

    public void placeOrders(){
        if(cartItemList.size()>0){
            String orderid = "RSMART-" + System.currentTimeMillis();
            List<OrderItem>  orderItems = new ArrayList<>();
            for(CartItem item : cartItemList){
                ordersDao.insert(new Orders(orderid,email,item.name,item.total_price));
                orderItems.add(new OrderItem(email,item.name,item.price,item.quantity));
            }
            Map<String,List<OrderItem>> orders = new HashMap<>();
            orders.put(orderid,orderItems);
            OrderModel orderModel = new OrderModel(orders);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("orders");
            DatabaseReference newRef = myRef.push();
            newRef.setValue(orders);
            Toast.makeText(this, "Order is Placed.", Toast.LENGTH_SHORT).show();
            for(Cart cart : carts){
                cartDao.deleteOrder(cart);
            }
            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            finish();
        }else{
            Toast.makeText(this, "There is no items in Cart.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Firstpagelogin.class));
            finish();
        }else if(item.getItemId() == R.id.viewCart){
            startActivity(new Intent(getApplicationContext(), ViewCartActivity.class));
            finish();
        }else if(item.getItemId() == R.id.viewOrders){
            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            finish();
        }else if(item.getItemId() == R.id.goHome){
            startActivity(new Intent(getApplicationContext(), home.class));
            finish();
        }else if(item.getItemId() == R.id.viewContact){
            startActivity(new Intent(getApplicationContext(), ContactActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
