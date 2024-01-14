package com.example.marketplace.dbStuff;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "marketplace.db";
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Implement onCreate and onUpgrade methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables for Marketplace, Product, and User
        db.execSQL(MarketplaceContract.CREATE_TABLE);
        db.execSQL(ProductContract.CREATE_TABLE);
        db.execSQL(UserContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // Drop existing tables and recreate
        db.execSQL("DROP TABLE IF EXISTS " + MarketplaceContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProductContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.TABLE_NAME);
        onCreate(db);
    }
}

