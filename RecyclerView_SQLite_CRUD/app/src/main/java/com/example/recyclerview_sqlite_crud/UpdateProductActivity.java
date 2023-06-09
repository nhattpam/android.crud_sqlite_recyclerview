package com.example.recyclerview_sqlite_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.lang.reflect.Type;
import java.util.List;

public class UpdateProductActivity extends AppCompatActivity {
// refresh list product
    private ProductAdapter productAdapter;
    private List<Product> productList;
    // refresh list product
    private EditText productNameEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Button updateButton;

    private DatabaseHelper databaseHelper;
    private int productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        productNameEditText = findViewById(R.id.productNameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        updateButton = findViewById(R.id.updateButton);

        databaseHelper = new DatabaseHelper(this);


        // Retrieve the productId from the intent
        productId = getIntent().getIntExtra("productId", -1);
        // Retrieve the position from the intent extras
        int position = getIntent().getIntExtra("position", -1);

        // Retrieve the product details from the database
        Product product = databaseHelper.getProduct(productId);

        //get list product
        String productListJson = getIntent().getStringExtra("productListJson");

        if (product != null) {
            // Set the existing values of the product in the EditText fields
            productNameEditText.setText(product.getProductName());
            descriptionEditText.setText(product.getDescription());
            priceEditText.setText(String.valueOf(product.getPrice()));
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values from the EditText fields
                String updatedProductName = productNameEditText.getText().toString();
                String updatedDescription = descriptionEditText.getText().toString();
                double updatedPrice = Double.parseDouble(priceEditText.getText().toString());

                // Create a new Product object with the updated values
                Product updatedProduct = new Product(productId, updatedProductName, updatedDescription, updatedPrice);

                // Update the product in the database
                boolean success = databaseHelper.updateProduct(updatedProduct);

                if (success) {
                    // Update the dataset in the adapter
                    Intent intent = new Intent(UpdateProductActivity.this, ProductActivity.class);
                    startActivity(intent);

                    Toast.makeText(UpdateProductActivity.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Finish the activity and go back to the previous screen
                } else {
                    Toast.makeText(UpdateProductActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
