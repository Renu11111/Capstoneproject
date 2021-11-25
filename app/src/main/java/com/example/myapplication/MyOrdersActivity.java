package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.myapplication.database.Orders;
import com.example.myapplication.database.OrdersDao;
import com.example.myapplication.database.RSMartDatabase;

public class MyOrdersActivity extends AppCompatActivity {

    TextView textView;
    RSMartDatabase rsMartDatabase;
    OrdersDao ordersDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        // Configure Database API
        rsMartDatabase = RSMartDatabase.getDatabase(getApplicationContext());
        ordersDao = rsMartDatabase.ordersDao();
        textView = findViewById(R.id.txt_orders);
        displayAllOrders();
    }

    public void displayAllOrders(){
        try {
            List<Orders> orders = ordersDao.findOrders(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            if(orders != null && orders.size()>0){
                Map<String,List<Orders>> allOrders = new LinkedHashMap<>();
                for(Orders order : orders){
                    if(allOrders.containsKey(order.orderid)){
                        allOrders.get(order.orderid).add(order);
                    }else{
                        List<Orders> cols = new ArrayList<>();
                        cols.add(order);
                        allOrders.put(order.orderid,cols);
                    }
                }
                String output = "";
                Set<String> allkeys = allOrders.keySet();
                for(String key : allkeys){
                    output +=  key + "\n\n";
                    for(Orders order : allOrders.get(key)){
                        output += " Product Name: " + order.name + "\n";
                        output += " Total Price: $" + order.price + "\n";
                        output += "\n";
                    }
                    output += "\n";
                }
                textView.setText(output);
            }else{
                textView.setText("No Orders Yet!!!");
            }
        }catch(Exception ex){
            textView.setText("No Orders Yet!!!");
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