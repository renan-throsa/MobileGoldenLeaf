package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
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
                val repository = MobileGoldenLeafDataBase.getInstance(it).productRepository
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
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0) ?: 0
        if (productId > 0) {
            presenter.loadBy(productId)
            dialog.setTitle(R.string.edit_product)
        }
        form_product_value.setOnEditorActionListener { _, i, _ -> handleKeyBoardEvent(i) }
        dialog.setTitle(R.string.add_product)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    override fun errorinvalidProduct() {

    }

    override fun errorSaveProduct() {
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
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0) ?: 0
        if (productId > 0) {
            product.id = productId
        }
        product.brand = form_product_value.toString()
        product.description = form_product_description.toString()
        product.code = form_product_code.toString()
        product.unitCost = convertValue(form_product_value.toString())
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

    override fun show(product: Product) {
        form_product_value.setText(product.unitCost.toString())
        form_product_brand.setText(product.brand)
        form_product_description.setText(product.description)
        form_product_code.setText(product.code)
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