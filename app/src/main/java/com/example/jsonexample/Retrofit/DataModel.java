package com.example.jsonexample.Retrofit;

import com.google.gson.annotations.SerializedName;

public class DataModel {

    private  int user_id;
    private int id;
    private String title;

    @SerializedName("completed")
    private boolean status;

    public int getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStatus() {
        return status;
    }
}
