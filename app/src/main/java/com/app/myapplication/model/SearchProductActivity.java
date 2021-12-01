package com.app.myapplication.model;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.ContactActivity;
import com.app.myapplication.Firstpagelogin;
import com.app.myapplication.MyOrdersActivity;
import com.app.myapplication.ProductDetailsActivity;
import com.app.myapplication.R;
import com.app.myapplication.ViewCartActivity;
import com.app.myapplication.adapter.ProductListAdapter;
import com.app.myapplication.home;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity implements View.OnClickListener{
    Category category;
    List<Product> products;
    List<Product> allProducts;
    FirebaseDatabase database;
    DatabaseReference productsRef;
    RecyclerView recyclerView;
    ProductListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText et_search;
    Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        recyclerView = findViewById(R.id.recylerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        et_search = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btn_search);

        products = new ArrayList<>();
        allProducts = new ArrayList<>();
        adapter = new ProductListAdapter(getApplicationContext(), products,SearchProductActivity.this);
        recyclerView.setAdapter(adapter);
        fetchProductDetails();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProduct();
            }
        });
    }

    public void searchProduct(){
        String search = et_search.getText().toString();
        if(search!=""){
            products.clear();
            for(Product product : allProducts){
                if(product.name.toLowerCase().startsWith(search.toLowerCase())){
                    products.add(product);
                }
            }
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "Please enter some search text", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchProductDetails(){
        database = FirebaseDatabase.getInstance();
        productsRef = database.getReference("products");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allProducts.clear();
                for(DataSnapshot keys:snapshot.getChildren()){
                    Product product = keys.getValue(Product.class);
                    products.add(product);
                    allProducts.add(product);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            Button button = (Button) v;
            if(button.getTag() != null && button.getTag() instanceof Product){
                Product product = (Product) button.getTag();
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product",product);
                startActivity(intent);
                finish();
            }
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
        }else if(item.getItemId() == R.id.et_search){
            Toast.makeText(this, "You already inside Search Activity.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
