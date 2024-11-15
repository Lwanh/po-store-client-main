package com.mware.polyshoprestapi.api;

import com.google.gson.JsonElement;
import com.mware.polyshoprestapi.models.Cart;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CartServices {
	@GET("cart/{idUser}")
	Call<JsonElement> getCart(@Path(("idUser")) String idUSer);
}
