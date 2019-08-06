package com.mithril.mobilegoldenleaf.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id",
        onDelete = SET_NULL))
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int category_id;
    private String brand;
    private String description;
    private String code;
    private double unit_cost;
    private boolean is_available;

    @Ignore
    public Product(int id, int category_id, String brand, String description, String code, double unit_cost, boolean is_available) {
        this.id = id;
        this.category_id = category_id;
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.unit_cost = unit_cost;
        this.is_available = is_available;
    }

    @Ignore
    public Product(int category_id, String brand, String description, String code, double unit_cost) {
        this.category_id = category_id;
        this.brand = brand;
        this.description = description;
        this.code = code;
        this.unit_cost = unit_cost;
        this.is_available = true;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return brand + '\'' + description + '\'' + code + '\'' + unit_cost;
    }

    public boolean hasValidId() {
        return this.getId() > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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
}
