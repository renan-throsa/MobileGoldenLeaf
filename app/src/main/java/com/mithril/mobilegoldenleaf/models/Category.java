package com.mithril.mobilegoldenleaf.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private  String title;


    @Ignore
    public Category(String title) {
        this.title = title;
    }

    @Ignore
    public Category(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Category() {

    }


    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
