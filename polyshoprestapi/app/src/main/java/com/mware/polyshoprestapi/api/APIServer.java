package com.mware.polyshoprestapi.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServer {
    private String IP = "192.168.6.4";
    private String PORT = "3001";
    public Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl("http://" + IP + ":" + PORT + "/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
