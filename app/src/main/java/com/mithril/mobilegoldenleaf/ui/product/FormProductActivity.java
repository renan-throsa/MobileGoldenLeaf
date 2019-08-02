package com.mithril.mobilegoldenleaf.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mithril.mobilegoldenleaf.R;
import com.mithril.mobilegoldenleaf.dao.ProductDAO;
import com.mithril.mobilegoldenleaf.models.Product;

public class FormProductActivity extends AppCompatActivity {

    private ProductDAO dao;
    private EditText brandEditText;
    private EditText descriptionEditText;
    private EditText valueEditText;
    private EditText codeEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);

        Button save_product_button = findViewById(R.id.activity_form_product_save_button);
        brandEditText = findViewById(R.id.activity_form_product_brand);
        descriptionEditText = findViewById(R.id.activity_form_product_description);
        valueEditText = findViewById(R.id.activity_form_product_value);
        codeEditText = findViewById(R.id.activity_form_product_code);

        save_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao = new ProductDAO();
                String brand = brandEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String value = valueEditText.getText().toString();
                String code = codeEditText.getText().toString();

                dao.save(
                        new Product(brand, description, code, Double.valueOf(value), true));

                startActivity(new Intent(FormProductActivity.this
                        , ProductsListActivity.class));
            }

        });
    }
}
