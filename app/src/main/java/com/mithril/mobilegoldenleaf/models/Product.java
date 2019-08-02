package com.mithril.mobilegoldenleaf.models;

public class Product {

    private int id;
    private String brand;
    private String description;
    private String code;
    private double unit_cost;
    private boolean is_available;


    public Product(String brand, String description, String code, double unit_cost, boolean is_available) {
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.unit_cost = unit_cost;
        this.is_available = is_available;
    }

    public Product(int id, String brand, String description, String code, double unit_cost, boolean is_available) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.unit_cost = unit_cost;
        this.is_available = is_available;
    }

    @Override
    public String toString() {
        return brand + '\'' + description + '\'' + code + '\'' + unit_cost + is_available;
    }
}
