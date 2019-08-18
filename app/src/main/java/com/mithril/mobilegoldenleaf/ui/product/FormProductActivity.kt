package com.mithril.mobilegoldenleaf.ui.product

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.database.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.ui.product.GoldenLeafConstants.Companion.PRODUCT_KEY

class FormProductActivity : AppCompatActivity() {
    private val dao by lazy {
        MobileGoldenLeafDataBase.getInstance(this).productDao
    }
    private val brandEditText: EditText = findViewById(R.id.activity_form_product_brand)
    private val descriptionEditText: EditText = findViewById(R.id.activity_form_product_description)
    private val valueEditText: EditText = findViewById(R.id.activity_form_product_value)
    private val codeEditText: EditText = findViewById(R.id.activity_form_product_code)
    private var product: Product? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)
        loadProduct()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_form_product_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_form_product_save_menu) {
            finishForm()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadProduct() {
        val intent = intent
        if (intent.hasExtra(PRODUCT_KEY)) {
            product = intent.getSerializableExtra(PRODUCT_KEY) as Product
            if (product != null) {
                title = EDIT_PRODUCT_TITLE
                brandEditText.setText(product!!.brand)
                descriptionEditText.setText(product!!.description)
                valueEditText.setText(product!!.unit_cost.toString())
                codeEditText.setText(product!!.code)
            }
        } else {
            title = NEW_PRODUCT_TITLE
            product = Product()
        }
    }

    private fun finishForm() {
        fillProductOut()
        if (product!!.hasValidId())
            dao.edit(product!!)
        else
            dao.save(product!!)
        finish()
    }



    private fun fillProductOut() {
        val brand = brandEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val value = valueEditText.text.toString()
        val code = codeEditText.text.toString()

        product!!.brand = brand
        product!!.description = description
        product!!.unit_cost = java.lang.Double.valueOf(value)
        product!!.code = code

    }

    companion object {
        private val NEW_PRODUCT_TITLE = "Novo produto"
        private val EDIT_PRODUCT_TITLE = "Editar produto"
    }
}
