package com.example.marketplace.dbStuff;

public class MarketplaceContract {

    public static final String TABLE_NAME = "marketplace";
    public static final String COLUMN_ID = "_id"; // Primary key
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "imageUrl";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_IMAGE_URL + " TEXT" +
                    ")";
}


