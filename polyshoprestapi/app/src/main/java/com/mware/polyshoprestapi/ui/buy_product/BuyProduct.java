package com.mware.polyshoprestapi.ui.buy_product;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mware.polyshoprestapi.R;
import com.mware.polyshoprestapi.api.APIServer;
import com.mware.polyshoprestapi.api.BillServices;
import com.mware.polyshoprestapi.models.Bill;
import com.mware.polyshoprestapi.models.Product;
import com.mware.polyshoprestapi.models.User;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyProduct extends AppCompatActivity {

    private ImageView imgPhoto;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvDescription;
    private ImageView imgBtnMinus;
    private TextView tvCount;
    private ImageView imgBtnPlus;
    private Button btnPay;

    int count = 1;

    SharedPreferences preferences;

    String formatVnd(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,### VND");
        String formattedAmount = decimalFormat.format(number);
        return formattedAmount;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

        preferences = getSharedPreferences("cookie", MODE_PRIVATE);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        imgBtnMinus = (ImageView) findViewById(R.id.imgBtnMinus);
        tvCount = (TextView) findViewById(R.id.tvCount);
        imgBtnPlus = (ImageView) findViewById(R.id.imgBtnPlus);
        btnPay = (Button) findViewById(R.id.btnPay);

        Intent intent = getIntent();

        Product product = (Product) intent.getSerializableExtra("product");

        Picasso.get().load(product.getPhoto()).into(imgPhoto);
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
        tvPrice.setText(formatVnd(product.getPrice()));

        tvCount.setText(count + "");

        btnPay.setText("Thanh Toán: " + formatVnd(count * product.getPrice()));

        imgBtnMinus.setOnClickListener(v -> {
            if (count <= 1) {
                count = 1;
            } else {
                count--;
            }
            btnPay.setText("Thanh Toán: " + formatVnd(count * product.getPrice()));

        });

        imgBtnPlus.setOnClickListener(v -> {
            count++;
            btnPay.setText("Thanh Toán: " + formatVnd(count * product.getPrice()));
        });

        btnPay.setOnClickListener(v -> {
            String cookie = preferences.getString("cookie", "");

            if (cookie.equals("")) {
                Toast.makeText(this, "Error to pay!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                User user = new Gson().fromJson(cookie, User.class);
                Bill bill = new Bill(product.get_id(), user.getEmail(), null, count, count * product.getPrice());
                BillServices services = new APIServer().RETROFIT.create(BillServices.class);

                Call<Bill> createBill = services.createBill(bill);

                createBill.enqueue(new Callback<Bill>() {
                    @Override
                    public void onResponse(Call<Bill> call, Response<Bill> response) {
                        if (response.body() != null) {
                            Bill billResponse = response.body();
                            startActivity(new Intent(BuyProduct.this, ThanhToanThanhCongActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Bill> call, Throwable t) {

                    }
                });
            }

        });
    }
}