package com.example.warehousemanagment.models;

public class Product {

    private String id;
    private String name;
    private String description;
    private String quantity;
    private String weight;

    public Product(){

    }

    public Product(String id, String name, String description, String quantity, String weight) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeight() {
        return weight;
    }
}
