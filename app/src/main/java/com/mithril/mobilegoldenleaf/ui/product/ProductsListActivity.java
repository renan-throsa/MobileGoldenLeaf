package com.mithril.mobilegoldenleaf.ui.product;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.dao.ProductDAO;

public class ProductsListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        setTitle("Lista de Produtos");


        ListView product_list = findViewById(R.id.product_list_listview);
        ProductDAO dao = new ProductDAO();
        product_list.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1
                , dao.get_product()));

    }


}
