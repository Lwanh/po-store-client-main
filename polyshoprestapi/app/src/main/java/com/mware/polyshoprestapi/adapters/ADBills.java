package com.mware.polyshoprestapi.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mware.polyshoprestapi.R;
import com.mware.polyshoprestapi.api.APIServer;
import com.mware.polyshoprestapi.api.ProductServices;
import com.mware.polyshoprestapi.models.Bill;
import com.mware.polyshoprestapi.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ADBills extends RecyclerView.Adapter<ADBills.ViewHolder> {

    private ArrayList<Bill> listBills = new ArrayList<>();
    private Context context;
    ProductServices services = new APIServer().RETROFIT.create(ProductServices.class);

    public ADBills(ArrayList<Bill> listBills, Context context) {
        this.listBills = listBills;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bill_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = listBills.get(holder.getAdapterPosition());

        Call<JsonElement> call = services.getProductById(bill.getProductId());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonElement jsonElement = response.body();

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    JsonObject productObject = jsonObject.getAsJsonObject("product");

                    Product product = new Gson().fromJson(productObject, Product.class);

                    holder.tvName.setText(product.getName());
                    Picasso.get().load(product.getPhoto()).into(holder.ivPhoto);


                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

//

        holder.tvPrice.setText(bill.getPrice() + "");
        holder.tvQuantity.setText("x" + bill.getQuantity());
        holder.tvCreatedAt.setText(bill.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return listBills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity, tvCreatedAt;
        private ImageView ivPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);

        }
    }
}
