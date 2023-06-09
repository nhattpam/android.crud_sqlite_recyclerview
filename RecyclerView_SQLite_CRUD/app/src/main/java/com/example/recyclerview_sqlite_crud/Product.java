package com.example.recyclerview_sqlite_crud;

public class Product {
    private int id;
    private String productName;
    private String description;
    private double price;

    public Product() {
    }

    public Product(int id, String productName, String description, double price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
