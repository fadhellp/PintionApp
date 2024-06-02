package com.example.finalproject.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Product {
    @SerializedName("name")
    private String name;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("concept")
    private List<String> concept;

    public String getName() {
        return name;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<String> getConcept() {
        return concept;
    }
}

