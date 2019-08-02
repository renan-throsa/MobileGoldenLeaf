package com.mithril.mobilegoldenleaf.dao;

import com.mithril.mobilegoldenleaf.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAO {

    private final static List<Product> products =
            new ArrayList<>(
                    Collections.singletonList(new Product("Qboa", "água sanitária", "123456", 3.50, true))
            );

    public void save(Product produc) {
        products.add(produc);
    }

    public List<Product> get_product() {
        return new ArrayList<>(products); // Send a copy, not a reference.
    }
}
