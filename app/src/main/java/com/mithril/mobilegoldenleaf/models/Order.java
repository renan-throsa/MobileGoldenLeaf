package com.mithril.mobilegoldenleaf.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int client_id;
    private int clerk_id;
    private Calendar date;
    private OrderStatus status;

    public Order() {
    }

    public Order(int client_id, int clerk_id, Calendar date, OrderStatus status) {
        this.client_id = client_id;
        this.clerk_id = clerk_id;
        this.date = date;
        this.status = status;
    }

    public Order(int id, int client_id, int clerk_id, Calendar date, OrderStatus status) {
        this.id = id;
        this.client_id = client_id;
        this.clerk_id = clerk_id;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getClerk_id() {
        return clerk_id;
    }

    public void setClerk_id(int clerk_id) {
        this.clerk_id = clerk_id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


}
