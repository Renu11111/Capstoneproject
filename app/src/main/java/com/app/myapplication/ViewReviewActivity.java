package com.app.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.adapter.ReviewListAdapter;
import com.app.myapplication.model.Product;
import com.app.myapplication.model.Review;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewReviewActivity extends AppCompatActivity {

    List<Review> reviews;
    FirebaseDatabase database;
    DatabaseReference reviewsRef;
    RecyclerView recyclerView;
    ReviewListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView textView;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        if(getIntent().getSerializableExtra("product") != null && getIntent().getSerializableExtra("product") instanceof Product){
            product = (Product) getIntent().getSerializableExtra("product");
        }
        database = FirebaseDatabase.getInstance();
        reviewsRef = database.getReference("reviews");

        reviews = new ArrayList<>();

        recyclerView = findViewById(R.id.recylerView);
        textView = findViewById(R.id.txt_product_name);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReviewListAdapter(this, reviews);
        recyclerView.setAdapter(adapter);

        fetchReviewDetails();
    }

    public void fetchReviewDetails(){
        if(product!=null){
            textView.setText("Review of Product (" + product.name + ")");
            reviewsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("IMPO","HERE");
                    reviews.clear();
                    for(DataSnapshot keys:snapshot.getChildren()){
                        Review review = keys.getValue(Review.class);
                        if(review!=null && review.name.equals(product.name)){
                            reviews.add(review);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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

