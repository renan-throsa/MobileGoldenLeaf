package com.mithril.mobilegoldenleaf.ui.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.dao.ProductDAO;
import com.mithril.mobilegoldenleaf.models.Product;

import java.util.List;

public class ProductsListActivity extends AppCompatActivity {

    ProductDAO dao = new ProductDAO();

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
        ListView product_list = findViewById(R.id.product_list_listview);
        configureList(product_list);

    }

    private void configureList(ListView product_list) {
        final List<Product> products = dao.get_product();
        product_list.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1
                , products));


        product_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product product = products.get(position);
                Intent intent = new Intent(ProductsListActivity.this, FormProductActivity.class);
                intent.putExtra("Product", product);
                startActivity(intent);
                return true;
            }
        });


    }


}
