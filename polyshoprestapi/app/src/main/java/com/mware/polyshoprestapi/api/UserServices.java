package com.mware.polyshoprestapi.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mware.polyshoprestapi.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserServices {
    @POST("auth/login")
    Call<JsonElement> authLogin(@Body JsonObject loginPayload);

    @PUT("user/update")
    Call<JsonElement> updateUser(@Body User user);
}

