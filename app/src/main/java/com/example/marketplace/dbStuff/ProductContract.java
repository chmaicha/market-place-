package com.example.marketplace.dbStuff;

public class ProductContract {

    public static final String TABLE_NAME = "product";
    public static final String COLUMN_ID = "_id"; // Primary key
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "imageUrl";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_MARKETPLACE_ID = "marketplace_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_IMAGE_URL + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_PRICE + " REAL," +
                    COLUMN_MARKETPLACE_ID + " INTEGER," +
                    "FOREIGN KEY(" + COLUMN_MARKETPLACE_ID + ") REFERENCES " +
                    MarketplaceContract.TABLE_NAME + "(" + MarketplaceContract.COLUMN_ID + ")" +
                    ")";
}

