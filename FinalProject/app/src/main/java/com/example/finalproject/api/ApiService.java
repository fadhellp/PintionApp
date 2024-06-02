package com.example.finalproject.api;

import com.example.finalproject.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    // Endpoint untuk mendapatkan daftar produk
    @Headers({
            // Header yang diperlukan untuk mengakses API RapidAPI
            "X-RapidAPI-Key: 7a05f24693msh5400a7f3b2540c6p172d53jsn92d4a44c32de",
            "X-RapidAPI-Host: apidojo-hm-hennes-mauritz-v1.p.rapidapi.com"
    })
    @GET("products/list") // Metode HTTP GET dengan endpoint "products/list"
    Call<ProductResponse> getProducts(
            // Parameter yang diperlukan untuk mengambil data produk
            @Query("country") String country, // Negara asal produk
            @Query("lang") String lang, // Bahasa yang digunakan
            @Query("currentpage") String currentPage, // Halaman saat ini
            @Query("pagesize") String pageSize // Jumlah item per halaman
    );
}
