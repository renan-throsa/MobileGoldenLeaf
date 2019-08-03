package com.mithril.mobilegoldenleaf.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.dao.ProductDAO;
import com.mithril.mobilegoldenleaf.models.Product;

public class FormProductActivity extends AppCompatActivity {

    public static final String TITLE = "Novo produto";
    private ProductDAO dao = new ProductDAO();
    private EditText brandEditText;
    private EditText descriptionEditText;
    private EditText valueEditText;
    private EditText codeEditText;
    private Button save_product_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);
        setTitle(TITLE);
        initializeFields();
        save_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = createProduct();
                dao.save(product);
                finish();
            }

        });

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("Product");
        brandEditText.setText(product.getBrand());
        descriptionEditText.setText(product.getDescription());
        valueEditText.setText(String.valueOf(product.getUnit_cost()));
        codeEditText.setText(product.getCode());
    }

    private void initializeFields() {
        save_product_button = findViewById(R.id.activity_form_product_save_button);
        brandEditText = findViewById(R.id.activity_form_product_brand);
        descriptionEditText = findViewById(R.id.activity_form_product_description);
        valueEditText = findViewById(R.id.activity_form_product_value);
        codeEditText = findViewById(R.id.activity_form_product_code);
    }

    private Product createProduct() {
        String brand = brandEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String value = valueEditText.getText().toString();
        String code = codeEditText.getText().toString();
        return new Product(brand, description, code, Double.valueOf(value), true);
    }
}
