package com.mware.polyshoprestapi.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mware.polyshoprestapi.R;
import com.mware.polyshoprestapi.ui.login.Login;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button btnRegister = findViewById(R.id.btn_register);
        EditText edtEmail = findViewById(R.id.edt_email);
        EditText edtPassword = findViewById(R.id.edt_password);

        // Xử lý sự kiện khi người dùng nhấn nút Đăng ký
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = edtEmail.getText().toString();
                String sPassword = edtPassword.getText().toString();

                if (sEmail.isEmpty()) {
                    Toast.makeText(Register.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                } else if (sPassword.isEmpty()) {
                    Toast.makeText(Register.this, "Password không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    dangkyFirebase(sEmail, sPassword);
                }
            }
        });
    }

    final String TAG = "RegisterActivity";

    // Đăng ký tài khoản Firebase
    private void dangkyFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Đăng ký thành công: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                            // Chuyển hướng đến màn hình chính sau khi đăng ký thành công
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Xử lý sự kiện nhấn vào link "Login"
    public void onLoginClick(View view) {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish(); // Kết thúc RegisterActivity khi chuyển sang MainActivity
    }
}