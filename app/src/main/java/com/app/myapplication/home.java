package com.app.myapplication;

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


import com.app.myapplication.adapter.CategoryAdapter;
import com.app.myapplication.dao.CategoryDAO;
import com.app.myapplication.model.Category;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;


public class home extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    CategoryAdapter adapter;
    List<Category> categories;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        categories = CategoryDAO.getAllCategories();
        recyclerView = findViewById(R.id.recylerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(this, categories,this);
        recyclerView.setAdapter(adapter);
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
            Toast.makeText(this, "You Already in Home Screen.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.viewContact){
            startActivity(new Intent(getApplicationContext(), ContactActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v instanceof Button){
            Button button = (Button) v;
            if(button.getTag() != null && button.getTag() instanceof Category){
                Category category = (Category) button.getTag();
                Intent intent = new Intent(getApplicationContext(),ProductActivity.class);
              //  intent.putExtra("category", category);
                startActivity(intent);
                finish();
            }
        }
    }
}