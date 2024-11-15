package com.mware.polyshoprestapi.api;

import com.mware.polyshoprestapi.models.Bill;
import com.mware.polyshoprestapi.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillServices {
    @POST("bill/create")
    Call<Bill> createBill(@Body Bill bill);

    @GET("bill/user/{email}")
    Call<ArrayList<Bill>> getBillsByUserEmail(@Path("email") String email);
}
