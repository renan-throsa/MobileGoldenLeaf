package com.mithril.mobilegoldenleaf.dao;

import com.mithril.mobilegoldenleaf.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDAO {

    private static int staticId = 1;
    private final static List<Product> products =
            new ArrayList<>(
                    Arrays.asList(new Product("Qboa", "Água sanitária", "12345687", 3.50, true),
                            new Product("Coca Cola", "Refrigerante Fanta Uva 2L", "12345678", 5.25, true))
            );

    public void save(Product product) {
        product.setId(staticId++);
        products.add(product);
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
