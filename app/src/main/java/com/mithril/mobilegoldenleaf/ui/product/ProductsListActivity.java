package com.mithril.mobilegoldenleaf.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mithril.mobilegoldenleaf.Database.MobileGoldenLeafDataBase;
import com.mithril.mobilegoldenleaf.Database.dao.ProductDao;
import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.adapters.ProductListAdapter;
import com.mithril.mobilegoldenleaf.models.Product;

import static com.mithril.mobilegoldenleaf.ui.product.Constants.PRODUCT_KEY;

public class ProductsListActivity extends AppCompatActivity {

    private static final String TITLE = "Lista de Produtos";
    private ProductDao dao;
    private ProductListAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = Room
                .databaseBuilder(ProductsListActivity.this, MobileGoldenLeafDataBase.class, "GoldenLeafDataBase.db")
                .allowMainThreadQueries()
                .build()
                .getProductDao();

        setContentView(R.layout.activity_products_list);
        setTitle(TITLE);
        configureList();

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
        updateProducts();
    }

    private void updateProducts() {
        adapter.update(dao.all());
    }

    private void configureList() {
        ListView product_list = findViewById(R.id.product_list_listview);
        configureAdapter(product_list);
        product_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product product = (Product) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(ProductsListActivity.this, FormProductActivity.class);
                intent.putExtra(PRODUCT_KEY, product);
                startActivity(intent);
                return true;
            }
        });

    }

    private void configureAdapter(ListView product_list) {
        adapter = new ProductListAdapter(this);
        product_list.setAdapter(adapter);
    }

}
