package com.mware.polyshoprestapi.api;

import com.google.gson.JsonElement;
import com.mware.polyshoprestapi.models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductServices {
    @GET("product/cate/{name}")
    Call<JsonElement> getProductByCate(@Path("name") String name);

    @GET("product/{id}")
    Call<JsonElement> getProductById(@Path("id") String productId);
}
