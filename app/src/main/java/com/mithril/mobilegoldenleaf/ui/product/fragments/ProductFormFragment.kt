package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val presenter = ProductFormPresenter(this,
            MobileGoldenLeafDataBase.getInstance(requireContext()).productRepository)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0) ?: 0
        presenter.loadBy(productId)
        form_product_value.setOnEditorActionListener { _, i, _ -> handleKeyBoardEvent(i) }

    }

    override fun errorinvalidProduct() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun errorSaveProduct() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        product.id = productId
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