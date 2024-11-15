package com.mware.polyshoprestapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mware.polyshoprestapi.R;
import com.mware.polyshoprestapi.models.Product;
import com.mware.polyshoprestapi.ui.buy_product.BuyProduct;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ADProducts extends RecyclerView.Adapter<ADProducts.ViewHolder> {

	private Context context;
	private ArrayList<Product> list = new ArrayList<>();
	private SharedPreferences sharedPreferences;


	public ADProducts() {
	}

	public ADProducts(Context context, ArrayList<Product> list) {
		sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
		this.context = context;
		this.list = list;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items, parent, false));
	}

	String formatVnd(int number) {
		DecimalFormat decimalFormat = new DecimalFormat("###,###,###,### VND");
		String formattedAmount = decimalFormat.format(number);
		return formattedAmount;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

		Product product = list.get(holder.getAdapterPosition());

		holder.tvName.setText(product.getName());
		Picasso.get().load(product.getPhoto()).into(holder.imgThumbnails);

		holder.tvDescription.setText(product.getDescription());
		holder.tvPrice.setText(formatVnd(product.getPrice()));

		holder.btnAddToCart.setOnClickListener(v -> {

			Intent intent = new Intent(context, BuyProduct.class);
			intent.putExtra("product", product);
			context.startActivity(intent);

//			String cookie = sharedPreferences.getString("cookie", "");
//			User user = new Gson().fromJson(cookie, User.class);
//
//			CartServices services = new APIServer().RETROFIT.create(CartServices.class);
//			Call<JsonElement> call = services.getCart(user.get_id());
//			call.enqueue(new Callback<JsonElement>() {
//				@Override
//				public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//					Toast.makeText(context, "Response: =====> " + response.body(), Toast.LENGTH_SHORT).show();
//				}
//
//				@Override
//				public void onFailure(Call<JsonElement> call, Throwable t) {
//
//				}
//			});

		});

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private ImageView imgThumbnails;
		private TextView tvPrice, tvName, tvDescription;
		private Button btnAddToCart;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			imgThumbnails = itemView.findViewById(R.id.imgThumbnails);
			tvName = itemView.findViewById(R.id.tvName);
			tvDescription = itemView.findViewById(R.id.tvDescription);
			tvPrice = itemView.findViewById(R.id.tvPrice);
			btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
		}
	}

}
