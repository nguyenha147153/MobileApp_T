package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity {
    TextView banner,error;
    EditText fullname,id,address,email,phone,password,confirmpass;
    ProgressBar progressBar;
    Button submit;
    FirebaseAuth auth;
    Account account;
    DatabaseReference dbref;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        matching();
        account = new Account();
        dbref = FirebaseDatabase.getInstance().getReference().child("DbAccount");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid = (snapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        auth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterAccount();

            }

            private void RegisterAccount() {
                String sfullname = fullname.getText().toString().trim();
                String semail = email.getText().toString().trim();
                String saddress = address.getText().toString().trim();
                String sphone = phone.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                String sconfirmpass = confirmpass.getText().toString().trim();


                if (sfullname.isEmpty()){
                    fullname.setError("Vui lo??ng ??i????n ??????y ??u?? th??ng tin");
                    fullname.requestFocus();
                    return;
                }if (semail.isEmpty()){
                    email.setError("Vui lo??ng ??i????n ??????y ??u?? th??ng tin");
                    email.requestFocus();
                    return;
                }if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
                    email.setError("Vui lo??ng nh????p ??u??ng ??i??nh da??ng");
                    email.requestFocus();
                    return;
                }if (saddress.isEmpty()){
                    address.setError("Vui lo??ng ??i????n ??????y ??u?? th??ng tin");
                    address.requestFocus();
                    return;
                }if (sphone.isEmpty()){
                    phone.setError("Vui lo??ng ??i????n ??????y ??u?? th??ng tin");
                    phone.requestFocus();
                    return;
                }if (sphone.length()<=9){
                    password.setError("S???? ??i????n thoa??i kh??ng ??u??ng");
                    password.requestFocus();
                    return;
                }if (sphone.length()>=11){
                    password.setError("M????t kh????u qua?? ng????n");
                    password.requestFocus();
                    return;
                }if (spassword.isEmpty()){
                    password.setError("Vui lo??ng ??i????n ??????y ??u?? th??ng tin");
                    password.requestFocus();
                    return;
                }if (sconfirmpass.isEmpty()) {
                    confirmpass.setError("Vui lo??ng ??i????n ??????y ??u?? th??ng tin");
                    confirmpass.requestFocus();
                    return;
                }if (spassword.length()<=6){
                    password.setError("M????t kh????u qua?? ng????n");
                    password.requestFocus();
                    return;
                }else if (!spassword.equals(sconfirmpass)){
                    confirmpass.setError("M????t kh????u kh??ng tru??ng kh????p");
                    confirmpass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Account account = new Account(sfullname, saddress, semail, spassword, sphone);
                            FirebaseDatabase.getInstance().getReference("DbAccount")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signup.this, "????ng ky?? ta??i khoa??n tha??nh c??ng", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(signup.this, login.class));
                                        progressBar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(signup.this, "????ng ky?? ta??i khoa??n th????t ba??i, ha??y th???? la??i",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"????ng ky?? ta??i khoa??n th????t ba??i",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }

    private void matching() {
        id = (EditText) findViewById(R.id.signup_et_id);
        fullname = (EditText) findViewById(R.id.signup_et_fullname);
        address = (EditText) findViewById(R.id.signup_et_address);
        email = (EditText) findViewById(R.id.signup_et_email);
        phone = (EditText) findViewById(R.id.signup_et_phone);
        password = (EditText) findViewById(R.id.signup_et_password);
        confirmpass = (EditText) findViewById(R.id.signup_et_confirmpass);
        submit = (Button) findViewById(R.id.signup_btn_submit);
        banner = (TextView) findViewById(R.id.signup_tv_banner);
        error = (TextView) findViewById(R.id.signup_tv_error);
        progressBar = (ProgressBar) findViewById(R.id.signup_progessbar);
    }
}