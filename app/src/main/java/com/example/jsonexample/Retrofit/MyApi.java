package com.example.jsonexample.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {

    @GET("/v3/d5f109b3-6e9c-4fea-a354-1130a09c10e1")
    Call<DataModel> getData();
}
