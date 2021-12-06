package com.example.mobileapplayout1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {
    EditText inputEmail;
    Button Submit;
    ProgressBar progressBar;
    FirebaseAuth auth;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        auth = FirebaseAuth.getInstance();
        matching();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
    }

    private void resetPass() {
        String sinEmail = inputEmail.getText().toString().trim();
        if (sinEmail.isEmpty()){
            inputEmail.setError("Vui lòng nhập email");
            inputEmail.requestFocus();
            return;
        }if (!Patterns.EMAIL_ADDRESS.matcher(sinEmail).matches()){
            inputEmail.setError("Vui lòng nhập đúng định dạng");
            inputEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(sinEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Forgotpassword.this,"Kiểm tra email để thay đổi mật khẩu!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Forgotpassword.this, login.class));
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(Forgotpassword.this,"Xảy ra lỗi, vui lòng thử lại!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void matching() {
        inputEmail = (EditText) findViewById(R.id.fp_et_emailforgot);
        Submit = (Button) findViewById(R.id.fp_btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.forgotpass_progressBar);
        Back = (ImageView) findViewById(R.id.fp_iv_back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}