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
import com.mithril.mobilegoldenleaf.delegate.CategoryDelegate
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import kotlinx.android.synthetic.main.category_form.view.*
import kotlinx.android.synthetic.main.product_form.view.*
import java.math.BigDecimal

abstract class FormCategoryDialog(private val context: Context, private val viewGroup: ViewGroup?) {
    private val viewCreated = buildLayout()
    protected val fieldTitle: EditText = viewCreated.form_category_title
    abstract val positiveButtonTitle: String


    fun call(categoryDelegate: CategoryDelegate) {
        configForm(categoryDelegate)
    }

    private fun configForm(categoryDelegate: CategoryDelegate) {
        AlertDialog.Builder(context)
                .setTitle(R.string.alter_category)
                .setView(viewCreated)
                .setPositiveButton(positiveButtonTitle) { _, _ ->
                    val description = fieldTitle.text.toString()
                    val category = Category(description)
                    categoryDelegate.delegate(category)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }


    private fun buildLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.product_form, viewGroup, false)
    }


}