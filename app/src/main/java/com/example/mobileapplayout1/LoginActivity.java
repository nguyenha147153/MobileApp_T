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

public class LoginActivity extends AppCompatActivity {
    TextView signup, error, forgotpass;
    EditText email, password;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        matching();
        Intent intent = new Intent(LoginActivity.this,Forgotpassword.class);
        Intent intent1 = new Intent(LoginActivity.this, signup.class);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, Profile.class));
            Toast.makeText(LoginActivity.this,"Bạn đã đăng nhập rồi",Toast.LENGTH_LONG).show();
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAccount();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void LoginAccount() {
        String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();
        if (semail.isEmpty()){
            email.setError("Vui lòng nhập email");
            email.requestFocus();
            return;
        }if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            email.setError("Vui lòng nhập đúng định dạng");
            email.requestFocus();
            return;
        }if (spassword.isEmpty()){
            password.setError("Vui lòng nhập mật khẩu");
            password.requestFocus();
            return;
        }if (spassword.length()<=6){
            password.setError("Sai tài khoản hoặc mật khẩu");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, index.class));
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void matching(){
        login = (Button) findViewById(R.id.login_btn_submit);
        signup = (TextView) findViewById(R.id.login_tv_signup);
        forgotpass = (TextView) findViewById(R.id.login_tv_forgot);
        email = (EditText) findViewById(R.id.login_et_email);
        password = (EditText) findViewById(R.id.login_et_password);
        error = (TextView) findViewById(R.id.login_tv_error);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
    }
}