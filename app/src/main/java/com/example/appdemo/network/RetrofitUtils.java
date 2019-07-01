package com.example.appdemo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private Retrofit retrofit;
    private static RetrofitUtils instance;

    private RetrofitUtils() {
    retrofit = new Retrofit.Builder()
            .baseUrl(APIStringRoot.API_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static RetrofitUtils getInstance() {
        if(instance == null){
            instance = new RetrofitUtils();
        }
        return instance;
    }

    public <ServiceClass> ServiceClass createService(Class<ServiceClass> serviceClass){
        return retrofit.create(serviceClass);
    }
}
