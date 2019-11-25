package com.mithril.mobilegoldenleaf.ui.product.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.extentions.toDecimalFormat
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductFormPresenter
import kotlinx.android.synthetic.main.dialogfragment_product_form.view.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition
import java.util.*


class ProductFormDialog(private val context: Context, private val viewGroup: ViewGroup?
                        , private val productId: Long = 0L) : ProductFormView {


    private val view = createView()
    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(context)
        ProductFormPresenter(this, repository)
    }

    private val NEGATIVE_BUTTON_TITLE = "Cancelar"

    private val DIALOG_TITLE: String
        get() {
            return if (productId != 0L) {
                "Editar produto"
            } else {
                "Nova categoria"
            }

        }
    private val POSITIVE_BUTTON_TITLE: String
        get() {
            return if (productId != 0L) {
                "Editar"
            } else {
                "Salvar"
            }

        }

    override fun productInvalidError() {
        val toast = Toast.makeText(context, R.string.product_invalid_error, Toast.LENGTH_SHORT)
        toast.show()

    }

    override fun savingProductError() {
        val toast = Toast.makeText(context, R.string.product_saving_error, Toast.LENGTH_SHORT)
        toast.show()
    }


    override fun showCategories(c: List<Category>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, c)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        view.form_category_spinner.adapter = adapter
    }


    fun show(productDelegate: (product: Product) -> Unit) {
        presenter.loadCategories()
        if (productId != 0L) {
            presenter.loadBy(productId)
        }
        AlertDialog.Builder(context)
                .setTitle(DIALOG_TITLE)
                .setView(view)
                .setPositiveButton(POSITIVE_BUTTON_TITLE) { _, _ ->
                    createProduct(productDelegate)
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    override fun show(product: Product) {
        with(view) {
            form_category_spinner.setSelection(getIndex(form_category_spinner, product.categoryId))
            form_product_value.setText(product.unitCost.toDecimalFormat())
            form_product_brand.setText(product.brand)
            form_product_description.setText(product.description)
            form_product_code.setText(product.code)
        }

    }

    private fun getIndex(spinner: Spinner?, categoryId: Long): Int {
        var index = 0
        for (i in 0 until spinner!!.count) {
            val category = spinner.getItemAtPosition(i) as Category
            if (category.id == categoryId) {
                index = i
            }
        }
        return index
    }


    private fun createProduct(productDelegate: (product: Product) -> Unit) {
        val product = saveProduct()
        if (product != null) {
            productDelegate(product)
        }

    }

    private fun saveProduct(): Product? {
        val product = Product()
        with(view) {
            product.categoryId = getCategoryFromSpinner()
            product.brand = form_product_brand.toString()
            product.description = form_product_description.toString()
            product.code = form_product_code.toString()
            product.unitCost = convertValue(form_product_value.toString())

        }
        if (productId != 0L) {
            product.id = productId
        }

        return if (presenter.save(product)) {
            product
        } else {
            null
        }

    }

    private fun convertValue(str: String): BigDecimal {
        return try {
            val ptBr = Locale("pt", "BR")
            val nf = NumberFormat.getInstance(ptBr) as DecimalFormat
            nf.isParseBigDecimal = true
            return nf.parse(str, ParsePosition(0)) as BigDecimal
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, R.string.value_error, Toast.LENGTH_LONG)
                    .show()
            BigDecimal.ZERO
        }
    }


    private fun getCategoryFromSpinner(): Long {
        with(view) {
            val position = form_category_spinner.selectedItemPosition
            val category = form_category_spinner.adapter.getItem(position) as Category
            return category.id
        }
    }

    private fun createView(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.dialog_category_form, viewGroup, false)

    }


}




