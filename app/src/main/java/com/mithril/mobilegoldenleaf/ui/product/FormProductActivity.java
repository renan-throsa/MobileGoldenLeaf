package com.mithril.mobilegoldenleaf.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.mithril.mobilegoldenleaf.database.MobileGoldenLeafDataBase;
import com.mithril.mobilegoldenleaf.database.dao.ProductDao;
import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.models.Product;

import static com.mithril.mobilegoldenleaf.ui.product.Constants.PRODUCT_KEY;

public class FormProductActivity extends AppCompatActivity {

    private static final String NEW_PRODUCT_TITLE = "Novo produto";
    private static final String EDIT_PRODUCT_TITLE = "Editar produto";
    private ProductDao dao;
    private EditText brandEditText;
    private EditText descriptionEditText;
    private EditText valueEditText;
    private EditText codeEditText;
    private Product product;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = Room
                .databaseBuilder(FormProductActivity.this, MobileGoldenLeafDataBase.class, "GoldenLeafDataBase.db")
                .allowMainThreadQueries()
                .build()
                .getProductDao();

        setContentView(R.layout.activity_form_product);
        initializeFields();
        loadProduct();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_form_product_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_form_product_save_menu) {
            finishForm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadProduct() {
        Intent intent = getIntent();
        if (intent.hasExtra(PRODUCT_KEY)) {
            product = (Product) intent.getSerializableExtra(PRODUCT_KEY);
            if (product != null) {
                setTitle(EDIT_PRODUCT_TITLE);
                brandEditText.setText(product.getBrand());
                descriptionEditText.setText(product.getDescription());
                valueEditText.setText(String.valueOf(product.getUnit_cost()));
                codeEditText.setText(product.getCode());
            }
        } else {
            setTitle(NEW_PRODUCT_TITLE);
            product = new Product();
        }
    }

    private void finishForm() {
        fillProductOut();
        if (product.hasValidId())
            dao.edit(product);
        else
            dao.save(product);
        finish();
    }

    private void initializeFields() {
        brandEditText = findViewById(R.id.activity_form_product_brand);
        descriptionEditText = findViewById(R.id.activity_form_product_description);
        valueEditText = findViewById(R.id.activity_form_product_value);
        codeEditText = findViewById(R.id.activity_form_product_code);
    }

    private void fillProductOut() {
        String brand = brandEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String value = valueEditText.getText().toString();
        String code = codeEditText.getText().toString();

        product.setBrand(brand);
        product.setDescription(description);
        product.setUnit_cost(Double.valueOf(value));
        product.setCode(code);

    }
}
