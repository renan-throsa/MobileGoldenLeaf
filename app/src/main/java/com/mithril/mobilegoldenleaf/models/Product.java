package com.mithril.mobilegoldenleaf.models;

import java.io.Serializable;

public class Product implements Serializable {

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    @Override
    public String toString() {
        return brand + '\'' + description + '\'' + code + '\'' + unit_cost + is_available;
    }
}
