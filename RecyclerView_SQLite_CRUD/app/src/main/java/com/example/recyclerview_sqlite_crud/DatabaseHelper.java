package com.example.recyclerview_sqlite_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "goods.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCT = "product";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_PRODUCT_NAME = "ProductName";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_PRICE = "Price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        long id = db.insert(TABLE_PRODUCT, null, values);
        if (id != -1) {
            product.setId((int) id);
        }

        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PRODUCT,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                Product product = new Product();
                product.setId(id);
                product.setProductName(productName);
                product.setDescription(description);
                product.setPrice(price);

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        int rowsAffected = db.update(
                TABLE_PRODUCT,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())}
        );

        db.close();
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        int rowsAffected = db.delete(
                TABLE_PRODUCT,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())}
        );

        db.close();
    }
}
