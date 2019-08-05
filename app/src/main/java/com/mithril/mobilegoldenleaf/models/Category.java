package com.mithril.mobilegoldenleaf.models;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private final String title;
    private final List<Product> products;

    public Category(String title) {
        this.title = title;
        products = new ArrayList<>();
    }

    public Category(int id, String title) {
        this.id = id;
        this.title = title;
        products = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                '}';
    }
}
