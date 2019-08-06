package com.mithril.mobilegoldenleaf.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(foreignKeys = {@ForeignKey(entity = Product.class,
        parentColumns = "id",
        childColumns = "product_id",
        onDelete = SET_NULL), @ForeignKey(entity = Order.class,
        parentColumns = "id",
        childColumns = "order_id",
        onDelete = CASCADE)})
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int order_id;
    private int product_id;
    private double quantity;
    private double extended_cost;

    public Item() {
    }

    public Item(int order_id, int product_id, double quantity, double extended_cost) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.extended_cost = extended_cost;
    }

    public Item(int id, int order_id, int product_id, double quantity, double extended_cost) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.extended_cost = extended_cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getExtended_cost() {
        return extended_cost;
    }

    public void setExtended_cost(double extended_cost) {
        this.extended_cost = extended_cost;
    }
}
