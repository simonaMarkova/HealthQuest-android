package com.example.simona.healthquest.network;

import com.example.simona.healthquest.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Simona on 7/18/2017.
 */

public class RetrofitManager {

    private static RetrofitManager instance;

    Retrofit retrofit;
    RetrofitInterface retrofitInterface;

    RetrofitManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }

    public RetrofitInterface getRetrofitService() {
        return retrofitInterface;
    }


}
