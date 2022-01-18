package com.example.a3lesson2.utils;

import android.app.Application;

import com.example.a3lesson2.data.remote.PostApi;
import com.example.a3lesson2.data.remote.RetrofitClient;

public class App extends Application {

    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideApi();
    }
}