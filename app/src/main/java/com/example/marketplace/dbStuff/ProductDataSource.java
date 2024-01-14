package com.example.marketplace.dbStuff;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.marketplace.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ProductDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insert a product into the database
    public long insertProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.COLUMN_NAME, product.getName());
        values.put(ProductContract.COLUMN_IMAGE_URL, product.getImageUrl());
        values.put(ProductContract.COLUMN_CATEGORY, product.getCategory());
        values.put(ProductContract.COLUMN_PRICE, product.getPrice());
        values.put(ProductContract.COLUMN_MARKETPLACE_ID, product.getMarketplaceId());

        return database.insert(ProductContract.TABLE_NAME, null, values);
    }

    // Get a list of products for a specific marketplace
    public List<Product> getProductsByMarketplaceId(long marketplaceId) {
        List<Product> productList = new ArrayList<>();

        String[] projection = {
                ProductContract.COLUMN_ID,
                ProductContract.COLUMN_NAME,
                ProductContract.COLUMN_IMAGE_URL,
                ProductContract.COLUMN_CATEGORY,
                ProductContract.COLUMN_PRICE
                // Add other columns as needed
        };

        String selection = ProductContract.COLUMN_MARKETPLACE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(marketplaceId)};

        Cursor cursor = database.query(
                ProductContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long productId = cursor.getLong(cursor.getColumnIndex(ProductContract.COLUMN_ID));
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_CATEGORY));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(ProductContract.COLUMN_PRICE));

                Product product = new Product(productName,imageUrl,price);
                product.setId(productId);
                product.setCategory(category);

                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return productList;
    }

    public List<Product> getProductsByCategory(String category_string) {
        List<Product> productList = new ArrayList<>();

        String[] projection = {
                ProductContract.COLUMN_ID,
                ProductContract.COLUMN_NAME,
                ProductContract.COLUMN_IMAGE_URL,
                ProductContract.COLUMN_CATEGORY,
                ProductContract.COLUMN_PRICE
        };

        String selection = ProductContract.COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = {category_string};

        Cursor cursor = database.query(
                ProductContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long productId = cursor.getLong(cursor.getColumnIndex(ProductContract.COLUMN_ID));
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_NAME));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_IMAGE_URL));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(ProductContract.COLUMN_CATEGORY));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(ProductContract.COLUMN_PRICE));

                Product product = new Product(productName,imageUrl,price);
                product.setId(productId);
                product.setCategory(category);

                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return productList;
    }


}

