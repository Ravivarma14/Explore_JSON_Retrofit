package com.example.jsonexample.Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.jsonexample.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonResponse extends AppCompatActivity {

    //https://run.mocky.io/v3/d5f109b3-6e9c-4fea-a354-1130a09c10e1

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_response);

        txt = findViewById(R.id.jsontv);

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://run.mocky.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MyApi myApi = retrofit.create(MyApi.class);

            Call<DataModel> response = myApi.getData();

            response.enqueue(new Callback<DataModel>() {
                @Override
                public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                    if (response.code() == 200) {
                        String str = "ID:" + response.body().getUser_id() + "\n Title:" + response.body().getTitle() + "\n status: " + response.body().isStatus();
                        txt.setText(str);
                    }
                }

                @Override
                public void onFailure(Call<DataModel> call, Throwable t) {

                }
            });
        }
        catch (Exception e){
            Log.d("varma",e.toString());
        }
    }

}