package com.example.apexonnewsupdate.rest;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient extends AppCompatActivity {

    //Local Host for emulator
    //10.0.2.2 is the default localhost for emulators in android studion
    public static final String BASE_URL = "http://10.0.2.2/wordpress/wp-json/api/";

    //Local Host for emulator
    //192.168.0.1 is the default localhost for real device in android studio base on your pc ip address
    public static final String BASE_URL_REAL = "http://192.168.0.187/wordpress/wp-json/api/";

    //Getting the retrofit api client
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }

}
