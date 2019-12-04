package com.mithril.mobilegoldenleaf.ui.product

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.extentions.toBrazilianFormat
import com.mithril.mobilegoldenleaf.extentions.toDecimalFormat
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.category.CategoryPresenter
import kotlinx.android.synthetic.main.dialog_product_form.view.*
import java.math.BigDecimal


class ProductFormDialog(private val context: Context,
                        private val viewGroup: ViewGroup?,
                        private val productId: Long = 0L) {


    private val view = createView()

    private val productPresenter by lazy {
        ProductPresenter(AppDataBase.getInstance(context).productRepository)
    }

    private val categoryPresenter by lazy {
        CategoryPresenter(AppDataBase.getInstance(context).categoryRepository)
    }

    private val NEGATIVE_BUTTON_TITLE = "Cancelar"
    private val POSITIVE_BUTTON_TITLE = "Salvar"

    private val DIALOG_TITLE: String
        get() {
            return if (productId != 0L) {
                "Editar produto"
            } else {
                "Novo produto"
            }

        }


    private fun showCategories(c: List<Category>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, c)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        view.form_category_spinner.adapter = adapter
    }


    fun show(whenSucceeded: (product: Product) -> Unit, whenFailed: (message: String?) -> Unit,
             categoryId: Long = 0L) {

        initCategoryDropDown(categoryId)
        if (productId != 0L) {
            productPresenter.get(productId,
                    whenSucceeded = { productFound ->
                        productFound?.let {
                            show(productFound)
                        }
                    }
            )
        }
        AlertDialog.Builder(context)
                .setTitle(DIALOG_TITLE)
                .setView(view)
                .setPositiveButton(POSITIVE_BUTTON_TITLE) { _, _ ->
                    saveProduct(whenSucceeded, whenFailed)
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    /*
    * This method decides weather or not categories drop down should be initialized in a specific way.
    */
    private fun initCategoryDropDown(categoryId: Long) {
        if (categoryId == 0L) {
            categoryPresenter.get(
                    whenSucceeded = { allCategories ->
                        showCategories(allCategories)
                    }, whenFailed = {
                val toast = Toast.makeText(context, R.string.getting_categories_error, Toast.LENGTH_SHORT)
                toast.show()
            }
            )
        } else {
            categoryPresenter.get(categoryId,
                    whenSucceeded = { categoryRetrieved ->
                        categoryRetrieved?.let {
                            showCategories(listOf(categoryRetrieved))
                        }
                    }
            )
        }
    }

    private fun show(product: Product) {
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


    private fun saveProduct(whenSucceeded: (product: Product) -> Unit, whenFailed: (message: String?) -> Unit) {
        val product = Product()
        with(view) {
            product.categoryId = getCategoryFromSpinner()
            product.brand = form_product_brand.text.toString()
            product.description = form_product_description.text.toString()
            product.code = form_product_code.text.toString()
            product.unitCost = convertValue(form_product_value.text.toString())

        }
        if (productId != 0L) {
            product.id = productId
            productPresenter.update(product, whenSucceeded, whenFailed)
        } else {
            productPresenter.save(product, whenSucceeded, whenFailed)
        }
    }

    private fun convertValue(str: String): BigDecimal {
        return try {
//            val ptBr = Locale("pt", "BR")
//            val nf = NumberFormat.getInstance(ptBr) as DecimalFormat
//            nf.isParseBigDecimal = true
//            return nf.parse(str, ParsePosition(0)) as BigDecimal

            return BigDecimal(str)
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
                .inflate(R.layout.dialog_product_form, viewGroup, false)

    }


}




