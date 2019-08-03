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
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);
        setTitle(TITLE);
        initializeFields();
        configureSaveButton();

        Intent intent = getIntent();
        if (intent.hasExtra("Product")) {
            product = (Product) intent.getSerializableExtra("Product");
            if (product != null) {

                brandEditText.setText(product.getBrand());
                descriptionEditText.setText(product.getDescription());
                valueEditText.setText(String.valueOf(product.getUnit_cost()));
                codeEditText.setText(product.getCode());
            }
        } else {
            product = new Product();
        }
    }

    private void configureSaveButton() {
        save_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillProductOut();
                if (product.hasValidId())
                    dao.edit(product);
                else
                    dao.save(product);
                finish();
            }

        });
    }

    private void initializeFields() {
        save_product_button = findViewById(R.id.activity_form_product_save_button);
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
