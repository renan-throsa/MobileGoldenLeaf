package com.mithril.mobilegoldenleaf.dao;

import com.mithril.mobilegoldenleaf.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAO {

    private static int staticId = 1;
    private final static List<Product> products =
            new ArrayList<>(
                    Collections.singletonList(new Product("Qboa", "água sanitária", "123456", 3.50, true))
            );

    public void save(Product produc) {
        produc.setId(staticId++);
        products.add(produc);
    }

    public List<Product> get_products() {
        return new ArrayList<>(products); // Send a copy, not a reference.
    }

    public void edit(Product product) {
        Product productFound = null;
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                productFound = p;
            }
        }
        if (productFound != null) {
            int position = products.indexOf(productFound);
            products.set(position, product);
        }
    }
}
