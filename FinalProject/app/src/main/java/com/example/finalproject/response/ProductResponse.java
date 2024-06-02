package com.example.finalproject.response;

import com.example.finalproject.models.Product;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductResponse {
    @SerializedName("results")
    private List<Product> results;

    public List<Product> getResults() {
        return results;
    }
}
