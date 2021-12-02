package com.app.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.myapplication.model.Product;

import com.app.myapplication.database.Cart;
import com.app.myapplication.database.CartDao;
import com.app.myapplication.database.RSMartDatabase;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetailsActivity extends AppCompatActivity {

    private Product product;
    private TextView txt_product_name,txt_product_description,txt_price,txt_sale_price;
    private ImageView image_product;
    private Button btn_add,btn_show_add_review,btn_view_review;
    private RSMartDatabase rsMartDatabase;
    private CartDao cartDao;
    FirebaseDatabase database;
    DatabaseReference reviewsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        // Configure Database API
        rsMartDatabase = RSMartDatabase.getDatabase(getApplicationContext());
        cartDao = rsMartDatabase.cartDao();
        database = FirebaseDatabase.getInstance();
        reviewsRef = database.getReference("reviews");
        // Fetch views
        txt_product_name = findViewById(R.id.txt_product_name);
        txt_product_description = findViewById(R.id.txt_product_description);
        txt_price = findViewById(R.id.txt_price);
        txt_sale_price = findViewById(R.id.txt_sale_price);
        image_product = findViewById(R.id.image_product);
        btn_add = findViewById(R.id.btn_add);
        btn_show_add_review = findViewById(R.id.btn_show_add_review);
        btn_view_review = findViewById(R.id.btn_view_review);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
        btn_show_add_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview(v);
            }
        });
        if(getIntent().getSerializableExtra("product")!=null && getIntent().getSerializableExtra("product") instanceof Product){
            product= (Product) getIntent().getSerializableExtra("product");
            txt_product_name.setText(product.name);
            txt_product_description.setText(product.description);
            txt_price.setText("$ " + product.price);
            txt_sale_price.setText("$" + product.sale_price);
           Glide.with(this).load(product.image_url).into(image_product);
        }
        btn_view_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToReview();
            }
        });
    }

    public void jumpToReview(){
        Intent intent = new Intent(getApplicationContext(), ViewReviewActivity.class);
        intent.putExtra("product",product);
        startActivity(intent);
        finish();
    }

    public void addToCart(){
        if(product!=null){
            try{
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Cart cart = new Cart(user.getEmail(),product.name,product.sale_price);
                cartDao.insert(cart);
                Toast.makeText(this, "Product is Added to Cart.", Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                Toast.makeText(this, "Product is not added in Cart" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void saveReview(View view){
        if(product!=null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AddReviewAction reviewAction = new AddReviewAction(product,user.getEmail(),reviewsRef);
            reviewAction.showPopupWindow(view);
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
