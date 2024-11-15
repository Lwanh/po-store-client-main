package com.mware.polyshoprestapi.ui.buy_product;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mware.polyshoprestapi.MainActivity;
import com.mware.polyshoprestapi.R;

public class ThanhToanThanhCongActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_thanh_cong);
        Toast.makeText(ThanhToanThanhCongActivity.this, "Thanh toan thanh cong!", Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnBackHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhToanThanhCongActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}