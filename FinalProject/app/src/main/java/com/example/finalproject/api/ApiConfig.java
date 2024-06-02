package com.example.finalproject.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://apidojo-hm-hennes-mauritz-v1.p.rapidapi.com/";

    // Metode untuk mendapatkan instance Retrofit client
    public static Retrofit getRetrofitClient() {
        // Cek apakah retrofit sudah diinisialisasi sebelumnya
        if (retrofit == null) {
            // Jika belum, inisialisasi OkHttpClient
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // Buat instance Retrofit dengan baseUrl dan converter Gson
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        // Kembalikan instance Retrofit client
        return retrofit;
    }
}
