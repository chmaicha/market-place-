package com.example.marketplace.dbStuff;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marketplace.model.Marketplace;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public MarketplaceDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertMarketplace(Marketplace marketplace) {
        ContentValues values = new ContentValues();
        values.put(MarketplaceContract.COLUMN_NAME, marketplace.getName());
        values.put(MarketplaceContract.COLUMN_IMAGE_URL, marketplace.getImageUrl());

        return database.insert(MarketplaceContract.TABLE_NAME, null, values);
    }

    public Marketplace getMarketplaceById(long marketplaceId) {
        Marketplace marketplace = null;

        // Define the columns you want to retrieve
        String[] projection = {
                MarketplaceContract.COLUMN_ID,
                MarketplaceContract.COLUMN_NAME,
                MarketplaceContract.COLUMN_IMAGE_URL
                // Add other columns you want to retrieve
        };

        // Specify the WHERE clause with the marketplace's ID
        String selection = MarketplaceContract.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(marketplaceId) };

        // Execute the query
        Cursor cursor = database.query(
                MarketplaceContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create a Marketplace object
            @SuppressLint("Range") String marketplaceName = cursor.getString(cursor.getColumnIndex(MarketplaceContract.COLUMN_NAME));
            @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(MarketplaceContract.COLUMN_IMAGE_URL));
            // Extract other columns as needed

            marketplace = new Marketplace(marketplaceName,imageUrl);
            marketplace.setId(marketplaceId);

            cursor.close();
        }

        return marketplace;
    }


    public List<Marketplace> getAllMarketplaces() {
        List<Marketplace> marketplaces = new ArrayList<>();

        Cursor cursor = database.query(
                MarketplaceContract.TABLE_NAME,
                null, // all columns
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract data from the cursor and create a Marketplace object
                @SuppressLint("Range") long marketId = cursor.getLong(cursor.getColumnIndex(MarketplaceContract.COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MarketplaceContract.COLUMN_NAME));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(MarketplaceContract.COLUMN_IMAGE_URL));

                Marketplace marketplace = new Marketplace(name,imageUrl);
                marketplace.setId(marketId);

                // Add the Marketplace to the list
                marketplaces.add(marketplace);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return marketplaces;
    }
}

