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
                    fullname.setError("Vui lòng điền đầy đủ thông tin");
                    fullname.requestFocus();
                    return;
                }if (semail.isEmpty()){
                    email.setError("Vui lòng điền đầy đủ thông tin");
                    email.requestFocus();
                    return;
                }if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
                    email.setError("Vui lòng nhập đúng định dạng");
                    email.requestFocus();
                    return;
                }if (saddress.isEmpty()){
                    address.setError("Vui lòng điền đầy đủ thông tin");
                    address.requestFocus();
                    return;
                }if (sphone.isEmpty()){
                    phone.setError("Vui lòng điền đầy đủ thông tin");
                    phone.requestFocus();
                    return;
                }if (sphone.length()<=9){
                    password.setError("Số điện thoại không đúng");
                    password.requestFocus();
                    return;
                }if (sphone.length()>=11){
                    password.setError("Mật khẩu quá ngắn");
                    password.requestFocus();
                    return;
                }if (spassword.isEmpty()){
                    password.setError("Vui lòng điền đầy đủ thông tin");
                    password.requestFocus();
                    return;
                }if (sconfirmpass.isEmpty()) {
                    confirmpass.setError("Vui lòng điền đầy đủ thông tin");
                    confirmpass.requestFocus();
                    return;
                }if (spassword.length()<=6){
                    password.setError("Mật khẩu quá ngắn");
                    password.requestFocus();
                    return;
                }else if (!spassword.equals(sconfirmpass)){
                    confirmpass.setError("Mật khẩu không trùng khớp");
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
                                        Toast.makeText(signup.this, "Đăng ký tài khoản thành công", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(signup.this, login.class));
                                        progressBar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(signup.this, "Đăng ký tài khoản thất bại, hãy thử lại",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"Đăng ký tài khoản thất bại",Toast.LENGTH_LONG).show();
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