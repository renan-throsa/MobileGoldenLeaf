package com.mithril.mobilegoldenleaf.ui.product.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Product
import kotlinx.android.synthetic.main.product_form.view.*
import java.math.BigDecimal

abstract class FormProductDialog(private val context: Context, private val viewGroup: ViewGroup?) {
    private val viewCreated = buildLayout()
    protected val fieldValue = viewCreated.form_product_value
    protected val fieldBrand = viewCreated.form_product_brand
    protected val fieldDescription = viewCreated.form_product_description
    protected val fieldCode = viewCreated.form_product_code
    protected val spinnerCategories = viewCreated.form_transacao_categoria
    abstract val positiveButtonTitle: String


    fun chama(productDelegate: ProductDelegate) {
        configCategoryBy()
        configuraFormulario(productDelegate)
    }

    private fun configuraFormulario(productDelegate: ProductDelegate) {
        AlertDialog.Builder(context)
                .setTitle(R.string.alter_product)
                .setView(viewCreated)
                .setPositiveButton(positiveButtonTitle) { _, _ ->
                    val brand = fieldBrand.text.toString()
                    val description = fieldDescription.text.toString()
                    val code = fieldCode.text.toString()
                    val valueInText = fieldValue.text.toString()
                    val categoryInText = spinnerCategories.selectedItem.toString()
                    val value = convertValue(valueInText)

                    val product =
                            Product(brand, description, code, value)

                    productDelegate.delegate(product)

                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun configCategoryBy(id: Int) {
        val categories = categoryBy(id)
        val adapter = ArrayAdapter.createFromResource(
                context,
                categories,
                android.R.layout.simple_dropdown_item_1line
        )
        spinnerCategories.adapter = adapter
    }

    protected fun categoryBy(id: Int): Unit {
    }


    private fun buildLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.product_form, viewGroup, false)

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