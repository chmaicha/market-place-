package com.example.marketplace.dbStuff;

public class UserContract {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "_id"; // Primary key
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_MARKETPLACE_ID = "marketplace_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_MARKETPLACE_ID + " INTEGER," +  // Ensure there is a space here
                    "FOREIGN KEY(" + COLUMN_MARKETPLACE_ID + ") REFERENCES " +
                    MarketplaceContract.TABLE_NAME + "(" + MarketplaceContract.COLUMN_ID + ")" +
                    ")";
}

