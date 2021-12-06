package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView Logout, Fullname, Address, Phone, Email, ViewFullname, ViewAddress, ViewPhone, ViewEmail, GreetingView;
    FirebaseUser user;
    BottomNavigationView bottomNavigationView;
    DatabaseReference reference;
    String uid;
    ImageView Back;
    Button Update, ResetPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        matching();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("DbAccount");
        uid = user.getUid();

        bottomNavigationView.setSelectedItemId(R.id.mnu_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        Toast.makeText(Profile.this,"Profile Here",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.mnu_products:
                        startActivity(new Intent(Profile.this, ProductsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_index:
                        startActivity(new Intent(Profile.this, index.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_history:
                        startActivity(new Intent(Profile.this, HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_setting:
                        startActivity(new Intent(Profile.this, logout.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, login.class));
                finish();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, UpdateProfileActivity.class));
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, index.class));
            }
        });
        ResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Forgotpassword.class));
            }
        });

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account != null){
                    String fullName = account.fullname;
                    String email = account.email;
                    String address = account.address;
                    String phone = account.phone;

                    GreetingView.setText("Welcome "+ fullName + "!");
                    ViewFullname.setText(fullName);
                    ViewEmail.setText(email);
                    ViewPhone.setText(phone);
                    ViewAddress.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void matching() {
        Fullname = (TextView) findViewById(R.id.pf_tv_fullname);
        Address = (TextView) findViewById(R.id.pf_tv_address);
        Phone = (TextView) findViewById(R.id.pf_tv_phone);
        Email = (TextView) findViewById(R.id.pf_tv_email);
        ViewFullname = (TextView) findViewById(R.id.pf_tv_vfullname);
        ViewAddress = (TextView) findViewById(R.id.pf_tv_vaddress);
        ViewPhone = (TextView) findViewById(R.id.pf_tv_vphone);
        ViewEmail = (TextView) findViewById(R.id.pf_tv_vemail);
        GreetingView = (TextView) findViewById(R.id.pf_tv_greetingview);
        Logout = (TextView) findViewById(R.id.pf_tv_logout);
        Back = (ImageView) findViewById(R.id.pf_iv_back);
        Update = (Button) findViewById(R.id.pf_btn_update);
        ResetPass = (Button) findViewById(R.id.pf_btn_resetpass);
        bottomNavigationView = findViewById(R.id.bottom_nav);
    }
}