package com.mithril.mobilegoldenleaf.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.dao.ProductDAO;
import com.mithril.mobilegoldenleaf.models.Product;

import static com.mithril.mobilegoldenleaf.ui.product.Constants.PRODUCT_KEY;

public class ProductsListActivity extends AppCompatActivity {

    public static final String TITLE = "Lista de Produtos";
    private ProductDAO dao = new ProductDAO();
    private ArrayAdapter<Product> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        arrayAdapter.clear();
        arrayAdapter.addAll(dao.get_products());

    }

    private void configureList() {
        ListView product_list = findViewById(R.id.product_list_listview);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        product_list.setAdapter(arrayAdapter);
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


}
