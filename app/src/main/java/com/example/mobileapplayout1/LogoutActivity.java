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

public class LogoutActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button Logout;
    TextView Gretting;
    FirebaseUser user;
    DatabaseReference reference;
    String uid;
    ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        Logout = (Button) findViewById(R.id.st_btn_logout);
        Gretting = (TextView) findViewById(R.id.st_tv_greeting);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("DbAccount");
        uid = user.getUid();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.mnu_setting);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnu_profile:
                        startActivity(new Intent(LogoutActivity.this, Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.mnu_index:
                        startActivity(new Intent(LogoutActivity.this, index.class));
                        return true;
                    case R.id.mnu_setting:
                        Toast.makeText(LogoutActivity.this,"Setting Here",Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account getname = snapshot.getValue(Account.class);
                if (getname != null){
                    String fname = getname.fullname;
                    Gretting.setText(fname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogoutActivity.this,"Lỗi database",Toast.LENGTH_LONG).show();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(LogoutActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}