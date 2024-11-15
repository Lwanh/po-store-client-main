package com.mware.polyshoprestapi.ui.home;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mware.polyshoprestapi.R;
import com.mware.polyshoprestapi.adapters.ADProducts;
import com.mware.polyshoprestapi.api.APIServer;
import com.mware.polyshoprestapi.api.CateServices;
import com.mware.polyshoprestapi.api.ProductServices;
import com.mware.polyshoprestapi.databinding.FragmentHomeBinding;
import com.mware.polyshoprestapi.loadmore.EndlessRecyclerViewScrollListener;
import com.mware.polyshoprestapi.models.Cate;
import com.mware.polyshoprestapi.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

	private CateServices cateServices;
	private ProductServices productServices;
	private FragmentHomeBinding binding;
	private ArrayList<Cate> cates;
	private ADProducts adProducts;
	private ArrayList<Product> products;

	public View onCreateView(@NonNull LayoutInflater inflater,
				 ViewGroup container, Bundle savedInstanceState) {

		cateServices = new APIServer().RETROFIT.create(CateServices.class);
		productServices = new APIServer().RETROFIT.create(ProductServices.class);

		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		cates = new ArrayList<>();
		Call<JsonElement> call = cateServices.getListCats();
		call.enqueue(new Callback<JsonElement>() {
			@Override
			public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
				if (response.code() != 200) {
					AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
					builder.setTitle("Error");
					builder.setMessage("Failed to fetch all category!");
					builder.show();
					return;
				}

				JsonElement jsonElement = response.body();

				JsonObject jsonObject = jsonElement.getAsJsonObject();
				JsonArray cateObj = jsonObject.getAsJsonArray("cats");

				cates.add(new Cate(null, "Tất cả"));

				for (JsonElement element : cateObj) {
					JsonObject object = element.getAsJsonObject();
					Cate cate = new Gson().fromJson(object, Cate.class);
					cates.add(cate);
				}

				ArrayAdapter<Cate> adapter = new ArrayAdapter<Cate>(binding.getRoot().getContext(), android.R.layout.simple_spinner_item, cates) {
					@NonNull
					@Override
					public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
						TextView textView = (TextView) super.getView(position, convertView, parent);
						textView.setText(cates.get(position).getName());
						return textView;
					}

					@Override
					public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
						TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
						textView.setText(cates.get(position).getName());
						return textView;
					}
				};

				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				binding.spinnerListCate.setAdapter(adapter);
				binding.spinnerListCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						if (cates.get(position).get_id() != null) {
							Cate cate = cates.get(position);
							performSortByCate(cate.getName());
						} else {
							performSortByCate(null);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						performSortByCate(null);
					}
				});
			}

			@Override
			public void onFailure(Call<JsonElement> call, Throwable t) {

			}
		});

		// sự kiện nhập và gửi chuỗi tìm kiếm
		binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				performSearch(query);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				performSearch(newText);
				return false;
			}
		});

		return root;
	}

	private void performSortByCate(String name) {
		Call<JsonElement> call;
		if (name != null) {
			// get products by id cat
			call = productServices.getProductByCate(name);
		} else {
			// get all products
			call = productServices.getProductByCate("all");
		}

		call.enqueue(new Callback<JsonElement>() {
			@Override
			public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
				/////////////////////////////////////////////
				if (response.code() != 200) {
					AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
					builder.setTitle("Error");
					builder.setMessage("Failed to fetch all category!");
					builder.show();
					return;
				}

				JsonElement jsonElement = response.body();

				JsonObject jsonObject = jsonElement.getAsJsonObject();
				JsonArray productObj = jsonObject.getAsJsonArray("products");
				products = new ArrayList<>();

				for (JsonElement element : productObj) {
					JsonObject object = element.getAsJsonObject();
					Product product = new Gson().fromJson(object, Product.class);
					products.add(product);
				}

				adProducts = new ADProducts(binding.getRoot().getContext(), products);
				GridLayoutManager layoutManager = new GridLayoutManager(binding.getRoot().getContext(), 2);
				binding.recycleProducts.setLayoutManager(layoutManager);
				binding.recycleProducts.setAdapter(adProducts);


			}

			@Override
			public void onFailure(Call<JsonElement> call, Throwable t) {

			}
		});


	}

	private void performSearch(String searchText) {
		// Xử lý tìm kiếm với chuỗi query
		ArrayList<Product> searchList = new ArrayList<>();

		if (searchText.trim().isEmpty()) {
			searchList.addAll(products);
		} else {
			String searchLowerCase = searchText.trim().toLowerCase();
			for (Product product : products) {
				if (product.getName().toLowerCase().contains(searchLowerCase)) {
					searchList.add(product);
				}
			}
		}

		adProducts = new ADProducts(binding.getRoot().getContext(), searchList);
		GridLayoutManager layoutManager = new GridLayoutManager(binding.getRoot().getContext(), 2);
		binding.recycleProducts.setLayoutManager(layoutManager);
		binding.recycleProducts.setAdapter(adProducts);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}