package com.mithril.mobilegoldenleaf.ui.product.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Product
import kotlinx.android.synthetic.main.fragment_product_form.view.*
import java.math.BigDecimal

abstract class FormProductDialog(private val context: Context, private val viewGroup: ViewGroup?) {
    private val viewCreated = buildLayout()
    protected val fieldValue: EditText = viewCreated.form_product_value
    protected val fieldBrand: EditText = viewCreated.form_product_brand
    protected val fieldDescription: EditText = viewCreated.form_product_description
    protected val fieldCode: EditText = viewCreated.form_product_code
    protected val spinnerCategories: Spinner = viewCreated.form_transacao_categoria
    abstract val positiveButtonTitle: String


    fun call(productDelegate: ProductDelegate) {
        configCategory()
        configForm(productDelegate)
    }

    private fun configForm(productDelegate: ProductDelegate) {
        AlertDialog.Builder(context)
                .setTitle(R.string.alter_product)
                .setView(viewCreated)
                .setPositiveButton(positiveButtonTitle) { _, _ ->
                    val brand = fieldBrand.text.toString()
                    val description = fieldDescription.text.toString()
                    val code = fieldCode.text.toString()
                    val valueInText = fieldValue.text.toString()
                    //val categoryInText = spinnerCategories.selectedItem.toString()
                    val value = convertValue(valueInText)

                    val product =
                            Product(1, brand, description, code, value)

                    productDelegate.delegate(product)

                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun configCategory() {
        val listOfItems = arrayOf("Item 1", "Item 2", "Item 3")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listOfItems)
        spinnerCategories.adapter = adapter
    }


    private fun buildLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.fragment_product_form, viewGroup, false)

    }

    private fun convertValue(valueToText: String): BigDecimal {
        return try {
            BigDecimal(valueToText)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Erro no valor inserido.", Toast.LENGTH_LONG)
                    .show()
            BigDecimal.ZERO
        }
    }
}