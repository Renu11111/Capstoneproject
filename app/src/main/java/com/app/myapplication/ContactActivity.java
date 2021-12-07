package com.app.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class ContactActivity extends AppCompatActivity {

    TextView textView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        textView=findViewById(R.id.txt_contact);
        btn = findViewById(R.id.btn_call);
        String message = "RS Mart\n\n";
        message += "546 York blvd\n";
        message += "Toronto, Ontario\n\n";
        message += "Phone: 7073891991\n\n";
        textView.setText(message);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                callNow();
            }
        });
    }

    public void callNow(){
        Uri phoneNumber = Uri.parse("tel:7073891991");
        Intent callIntent = new Intent(Intent.ACTION_DIAL,phoneNumber);
        startActivity(callIntent);
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
            Toast.makeText(this, "You Already in Contact Us Screen.", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.viewSearch){
            Toast.makeText(this, "You already inside Search Activity.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
