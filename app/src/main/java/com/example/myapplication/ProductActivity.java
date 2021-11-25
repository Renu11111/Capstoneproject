package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductListAdapter;
import model.Category;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    Category category;
    List<Product> products;
    FirebaseDatabase database;
    DatabaseReference productsRef;
    RecyclerView recyclerView;
    ProductListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        if (getIntent().getSerializableExtra("category") != null && getIntent().getSerializableExtra("category") instanceof Category) {
            category = (Category) getIntent().getSerializableExtra("category");
        }
        recyclerView = findViewById(R.id.recylerView);
        layoutManager = new LinearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        products = new ArrayList<>();
        adapter = new ProductListAdapter(getApplicationContext(), products, ProductActivity.this);
        recyclerView.setAdapter(adapter);
        fetchProductDetails();
    }

    private void fetchProductDetails() {
        database = FirebaseDatabase.getInstance();
        productsRef = database.getReference("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("IMPO", "HERE");
                products.clear();
                for (DataSnapshot keys : snapshot.getChildren()) {
                    Product product = keys.getValue(Product.class);
                    if (category != null && category.getCategoryId().equals(product.category_id)) {
                        products.add(product);
                    }
                }
                Log.d("IMPO", products.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            if (button.getTag() != null && button.getTag() instanceof Product) {
                Product product = (Product) button.getTag();
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra( "product", product);
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
        } else if (item.getItemId() == R.id.viewCart) {
            startActivity(new Intent(getApplicationContext(), ViewCartActivity.class));
            finish();
        } else if (item.getItemId() == R.id.viewOrders) {
            startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            finish();
        } else if (item.getItemId() == R.id.goHome) {
            startActivity(new Intent(getApplicationContext(), home.class));
            finish();
        } else if (item.getItemId() == R.id.viewContact) {
            startActivity(new Intent(getApplicationContext(), ContactActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
