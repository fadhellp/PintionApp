package com.example.finalproject.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.adapter.ProductAdapter;
import com.example.finalproject.api.ApiConfig;
import com.example.finalproject.api.ApiService;
import com.example.finalproject.models.Product;
import com.example.finalproject.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button btnLoadMore;
    private ProgressBar progressBar;
    private int currentPage = 1;
    private int totalItemsToShow = 10; // Jumlah item yang ditampilkan awalnya
    private int itemsPerPage = 15; // Jumlah item yang akan dimuat lebih banyak
    private Handler handler;
    private SharedPreferences sharedPreferences;
    private EditText editTextSearch; // Tambahkan EditText untuk pencarian

    public ProductFragment() {
        // Konstruktor publik kosong yang diperlukan oleh Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        btnLoadMore = view.findViewById(R.id.btn_load_more);
        editTextSearch = view.findViewById(R.id.edit_text_search); // Inisialisasi EditText

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        handler = new Handler(Looper.getMainLooper());

        // Inisialisasi daftar produk
        productList = new ArrayList<>(); // Anda perlu mengisi daftar ini dengan produk Anda

        // Inisialisasi adapter
        productAdapter = new ProductAdapter(getContext(), productList);

        // Set adapter ke RecyclerView
        recyclerView.setAdapter(productAdapter);

        // Memuat produk awal
        loadInitialProducts();

        // Menetapkan listener klik untuk tombol Load More
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreProducts();
            }
        });

        // Menetapkan listener untuk EditText pencarian
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Tidak diperlukan
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Memfilter produk berdasarkan kueri pencarian
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Tidak diperlukan
            }
        });

        return view;
    }

    // Metode untuk memfilter produk berdasarkan kueri pencarian
    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.filterList(filteredList);
    }

    // Metode untuk memuat produk awal
    private void loadInitialProducts() {
        // Menampilkan ProgressBar dan menyembunyikan tombol Load More saat produk sedang dimuat
        progressBar.setVisibility(View.VISIBLE);
        btnLoadMore.setVisibility(View.GONE);
        editTextSearch.setVisibility(View.GONE); // Sembunyikan bilah pencarian

        ApiService apiService = ApiConfig.getRetrofitClient().create(ApiService.class);
        Call<ProductResponse> call = apiService.getProducts("us", "en", String.valueOf(currentPage), String.valueOf(totalItemsToShow));
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse != null) {
                        productList.addAll(productResponse.getResults());
                        productAdapter.notifyDataSetChanged();
                    }
                }
                // Menyembunyikan ProgressBar setelah mendapatkan respons dari API
                progressBar.setVisibility(View.GONE);
                // Menampilkan kembali tombol Load More setelah ProgressBar hilang
                btnLoadMore.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.VISIBLE); // Tampilkan kembali bilah pencarian
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
                // Menyembunyikan ProgressBar jika terjadi kegagalan saat memuat produk
                progressBar.setVisibility(View.GONE);
                // Menampilkan kembali tombol Load More setelah ProgressBar hilang
                btnLoadMore.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.VISIBLE); // Tampilkan kembali bilah pencarian
            }
        });
    }

    // Metode untuk memuat lebih banyak produk
    private void loadMoreProducts() {
        // Menampilkan ProgressBar saat produk tambahan sedang dimuat
        progressBar.setVisibility(View.VISIBLE);
        // Menyembunyikan tombol Load More saat produk tambahan sedang dimuat
        btnLoadMore.setVisibility(View.GONE);
        editTextSearch.setVisibility(View.GONE); // Sembunyikan bilah pencarian

        currentPage++; // Increment nomor halaman
        int startIndex = totalItemsToShow + 1; // Indeks awal untuk memuat lebih banyak item
        totalItemsToShow += itemsPerPage; // Perbarui total item yang ditampilkan

        ApiService apiService = ApiConfig.getRetrofitClient().create(ApiService.class);
        Call<ProductResponse> call = apiService.getProducts("us", "en", String.valueOf(currentPage), String.valueOf(itemsPerPage));
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse != null) {
                        productList.addAll(productResponse.getResults());
                        productAdapter.notifyItemRangeInserted(startIndex, itemsPerPage);
                    }
                }
                // Menyembunyikan ProgressBar setelah mendapatkan respons dari API
                progressBar.setVisibility(View.GONE);
                // Menampilkan kembali tombol Load More setelah ProgressBar hilang
                btnLoadMore.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.VISIBLE); // Tampilkan kembali bilah pencarian
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
                // Menyembunyikan ProgressBar jika terjadi kegagalan saat memuat produk tambahan
                progressBar.setVisibility(View.GONE);
                // Menampilkan kembali tombol Load More setelah ProgressBar hilang
                btnLoadMore.setVisibility(View.VISIBLE);
                editTextSearch.setVisibility(View.VISIBLE); // Tampilkan kembali bilah pencarian
            }
        });
    }
}
