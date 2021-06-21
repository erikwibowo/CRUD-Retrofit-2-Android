package com.sebamitradata.laundry.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    private static final String base_url = "http://192.168.32.154:8000/api/";
    private static Retrofit retrofit;

    public static Retrofit connection(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
