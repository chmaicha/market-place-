package com.example.marketplace.model;

public class User {
    private long id;
    private String name;
    private String password;
    private Marketplace marketplace;
    private long marketplaceId;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.marketplace = new Marketplace(
                this.name + "'s market",
                "https://images.pexels.com/photos/942320/pexels-photo-942320.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        );
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

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
