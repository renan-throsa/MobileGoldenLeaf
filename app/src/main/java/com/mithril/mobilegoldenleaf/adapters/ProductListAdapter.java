package com.mithril.mobilegoldenleaf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends BaseAdapter {

    private final List<Product> products = new ArrayList<>();
    private Context context;

    public ProductListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View createdView = LayoutInflater
                .from(context)
                .inflate(R.layout.item_product, viewGroup, false);
        Product p = products.get(position);

        TextView brand = createdView.findViewById(R.id.item_product_brand);
        brand.setText(p.getBrand());

        TextView description = createdView.findViewById(R.id.item_product_description);
        description.setText(p.getDescription());

        TextView value = createdView.findViewById(R.id.item_product_value);
        value.setText(String.valueOf(p.getUnit_cost()));

        TextView code = createdView.findViewById(R.id.item_product_code);
        code.setText(p.getCode());

        return createdView;
    }

    public void clear() {
        products.clear();
    }

    public void addAll(List<Product> products) {
        this.products.addAll(products);
    }
}

