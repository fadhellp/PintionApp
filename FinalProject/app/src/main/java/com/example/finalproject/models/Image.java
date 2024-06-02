package com.example.finalproject.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("baseUrl")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}
