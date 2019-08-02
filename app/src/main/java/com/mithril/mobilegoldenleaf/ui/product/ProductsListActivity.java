package com.mithril.mobilegoldenleaf.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.dao.ProductDAO;

public class ProductsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        setTitle("Lista de Produtos");


        FloatingActionButton new_product_fba = findViewById(R.id.activity_products_list_fab_new_product);
        new_product_fba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductsListActivity.this, FormProductActivity.class));
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        ProductDAO dao = new ProductDAO();
        ListView product_list = findViewById(R.id.product_list_listview);
        product_list.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1
                , dao.get_product()));

    }
}
