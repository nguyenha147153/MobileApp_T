package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText fullname, eMail, addRess, moBile;
    Button update, cancel;
    String uid;
    FirebaseUser user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        matching();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("DbAccount");
        uid = user.getUid();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setUserInfo();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String sfname = fullname.getText().toString().trim();
                            String saddress = addRess.getText().toString().trim();
                            String semail = eMail.getText().toString().trim();
                            String smobile = moBile.getText().toString().trim();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("DbAccount");

                            reference.child(uid).child("fullname").setValue(sfname);
                            reference.child(uid).child("address").setValue(saddress);
                            reference.child(uid).child("email").setValue(semail);
                            reference.child(uid).child("mobile").setValue(smobile);
                            Toast toast = Toast.makeText(getApplicationContext(), "Cập nhật thông tin thành công", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 100, 200);
                            toast.show();
                            startActivity(new Intent(UpdateProfileActivity.this, Profile.class));
                        } catch (Exception e) {
                            Log.d("error update", e.toString());
                        }
                    }
                });
            }
        });
    }

    private void setUserInfo() {
        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account userProfile = snapshot.getValue(Account.class);
                if (userProfile != null){
                    String fullName = userProfile.fullname;
                    String email = userProfile.email;
                    String address = userProfile.address;
                    String phone = userProfile.phone;

                    fullname.setText(fullName);
                    eMail.setText(email);
                    moBile.setText(phone);
                    addRess.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this,"Lỗi database",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void matching() {
        fullname = (EditText) findViewById(R.id.upf_et_fullname);
        eMail = (EditText) findViewById(R.id.upf_et_email);
        addRess = (EditText) findViewById(R.id.upf_et_address);
        moBile = (EditText) findViewById(R.id.upf_et_phone);
        update = (Button) findViewById(R.id.upf_btn_update);
        cancel = (Button) findViewById(R.id.upf_btn_cancel);
    }
}