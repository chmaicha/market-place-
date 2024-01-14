package com.example.marketplace.dbStuff;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marketplace.model.Marketplace;
import com.example.marketplace.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private MarketplaceDataSource marketplaceDataSource;

    public UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
        marketplaceDataSource = new MarketplaceDataSource(context);
        marketplaceDataSource.open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_NAME, user.getName());
        values.put(UserContract.COLUMN_PASSWORD, user.getPassword());
        values.put(UserContract.COLUMN_MARKETPLACE_ID, user.getMarketplaceId());

        return database.insert(UserContract.TABLE_NAME, null, values);
    }

    public User getUserByName(String userName) {
        User user = null;

        // Define the columns you want to retrieve
        String[] projection = {
                UserContract.COLUMN_ID,
                UserContract.COLUMN_NAME,
                UserContract.COLUMN_PASSWORD,
                UserContract.COLUMN_MARKETPLACE_ID
        };

        // Specify the WHERE clause with the user's name
        String selection = UserContract.COLUMN_NAME + " = ?";
        String[] selectionArgs = { userName };

        // Execute the query
        Cursor cursor = database.query(
                UserContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor and create a User object
            @SuppressLint("Range") long userId = cursor.getLong(cursor.getColumnIndex(UserContract.COLUMN_ID));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_PASSWORD));
            @SuppressLint("Range") long marketplaceId = cursor.getLong(cursor.getColumnIndex(UserContract.COLUMN_MARKETPLACE_ID));

            user = new User(userName,password);
            user.setId(userId);
            user.setMarketplaceId(marketplaceId);
            Marketplace marketplace = marketplaceDataSource.getMarketplaceById(marketplaceId);
            user.setMarketplace(marketplace);

            cursor.close();
        }

        return user;
    }
}

