package com.mware.polyshoprestapi.api;

import com.google.gson.JsonElement;
import com.mware.polyshoprestapi.models.Cate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CateServices {
	//	get list category
	@GET("cats")
	Call<JsonElement> getListCats();
}
