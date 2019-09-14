package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductFormPresenter
import kotlinx.android.synthetic.main.fragment_product_form.*
import java.math.BigDecimal

class ProductFormFragment : DialogFragment(), ProductFormView {

    private val presenter by lazy {
        context.let {
            if (it != null) {
                val repository = MobileGoldenLeafDataBase.getInstance(it)
                ProductFormPresenter(this, repository)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSpinner()
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0L) ?: 0L
        if (productId != 0L) {
            presenter.loadBy(productId)
            dialog.setTitle(R.string.edit_product)
        }

        form_product_value.setOnEditorActionListener { _, i, _ -> handleKeyBoardEvent(i) }
        dialog.setTitle(R.string.add_product)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    private fun configureSpinner() {
        presenter.loadCategories()
    }


    override fun productInvalidError() {
        val toast = Toast.makeText(context, R.string.product_invalid_error, Toast.LENGTH_SHORT)
        toast.show()

    }

    override fun savingProductError() {
        val toast = Toast.makeText(context, R.string.product_saving_error, Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun handleKeyBoardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            val product = saveProduct()
            if (product != null) {
                if (activity is OnProductSavedListener) {
                    val listener = activity as OnProductSavedListener
                    listener.onProductSaved(product)
                }
            }
            dialog.dismiss()
            return true
        }
        return false
    }

    private fun saveProduct(): Product? {
        val product = Product()
        product.categoryId = form_category_spinner.selectedItemPosition.toLong()
        product.brand = form_product_value.text.toString()
        product.description = form_product_description.text.toString()
        product.code = form_product_code.text.toString()
        product.unitCost = convertValue(form_product_value.text.toString())
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0) ?: 0

        if (productId != 0L) {
            product.id = productId
        }
        return if (presenter.save(product)) {
            product
        } else {
            null
        }

    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
    }

    override fun showProduct(product: Product) {
        form_category_spinner.setSelection(product.categoryId.toInt())
        form_product_value.setText(product.unitCost.toString())
        form_product_brand.setText(product.brand)
        form_product_description.setText(product.description)
        form_product_code.setText(product.code)
    }

    override fun showCategories(c: List<Category>) {
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, c) }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        form_category_spinner.adapter = adapter
    }

    private fun convertValue(valueToText: String): BigDecimal {
        return try {
            BigDecimal(valueToText)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, R.string.value_error, Toast.LENGTH_LONG)
                    .show()
            BigDecimal.ZERO
        }
    }


    companion object {
        private const val DIALOG_TAG = "productId"
        private const val EXTRA_PRODUCT_ID = "editDialog"

        fun newInstance(id: Long = 0): ProductFormFragment {
            val fragment = ProductFormFragment()
            val args = Bundle()
            args.putLong(EXTRA_PRODUCT_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}