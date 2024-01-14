package com.example.marketplace.model;

public class Product {
    private long id;
    private String name;
    private String imageUrl;
    private String category;
    private Double price;
    private long marketplaceId;

    public Product(String name, String imageUrl, Double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(long marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
