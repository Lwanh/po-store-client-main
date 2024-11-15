package com.mware.polyshoprestapi.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mware.polyshoprestapi.MainActivity;
import com.mware.polyshoprestapi.R;
import com.mware.polyshoprestapi.api.APIServer;
import com.mware.polyshoprestapi.api.UserServices;
import com.mware.polyshoprestapi.models.User;
import com.mware.polyshoprestapi.ui.home.HomeFragment;
import com.mware.polyshoprestapi.ui.register.Register;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login); // Đảm bảo rằng layout này tồn tại

		mAuth = FirebaseAuth.getInstance();

		Button btnLogin = findViewById(R.id.btn_login);
		Button btnRegister = findViewById(R.id.btn_register);
		EditText edtEmail = findViewById(R.id.edt_email);
		EditText edtPassword = findViewById(R.id.edt_password);

		// Xử lý sự kiện khi người dùng nhấn nút Đăng nhập
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String sEmail = edtEmail.getText().toString();
				String sPassword = edtPassword.getText().toString();

				if (sEmail.isEmpty()) {
					Toast.makeText(Login.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
				} else if (sPassword.isEmpty()) {
					Toast.makeText(Login.this, "Password không được để trống", Toast.LENGTH_SHORT).show();
				} else {
					dangnhapFirebase(sEmail, sPassword);
				}
			}
		});

		// Chuyển sang màn đăng ký
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Login.this, Register.class);
				startActivity(intent);
			}
		});
	}

	final String TAG = "Login";

	// Đăng nhập Firebase
	private void dangnhapFirebase(String email, String password) {
		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInWithEmail:success");
							FirebaseUser user = mAuth.getCurrentUser ();
							Toast.makeText(Login.this, "Đăng nhập thành công: " + user.getEmail(), Toast.LENGTH_SHORT).show();

							// Chuyển hướng sang màn hình chính
							Intent intent = new Intent(Login.this, MainActivity.class);
							startActivity(intent);
							finish();
						} else {
							Log.w(TAG, "signInWithEmail:failure", task.getException());
							Toast.makeText(Login.this, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}