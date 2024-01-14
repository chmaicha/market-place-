package com.example.marketplace.model;

import java.util.ArrayList;
import java.util.List;

public class Marketplace {
    private long id;
    private String name;
    private String imageUrl;
    private List<Product> productList;

    public Marketplace(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.productList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
