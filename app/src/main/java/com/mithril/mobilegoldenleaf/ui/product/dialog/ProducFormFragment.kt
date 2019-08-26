package com.mithril.mobilegoldenleaf.ui.product.dialog

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
import com.mithril.mobilegoldenleaf.interfaces.onProductSavedListener
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import com.mithril.mobilegoldenleaf.ui.product.presentation.ProductFormPresenter
import kotlinx.android.synthetic.main.fragment_product_form.*
import java.math.BigDecimal

class ProducFormFragment : DialogFragment(), ProductFormView {

    private val dao by lazy { MobileGoldenLeafDataBase.getInstance(requireContext()).productRepository }
    private val presenter = ProductFormPresenter(this, dao)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0) ?: 0
        presenter.loadBy(productId)
        dialog.setTitle(R.string.add_product)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    override fun show(product: Product) {
        form_product_value.setText(product.unitCost.toString())
        form_product_brand.setText(product.brand)
        form_product_description.setText(product.description)
        form_product_code.setText(product.code)
    }

    override fun errorinvalidProduct() {
        Toast.makeText(requireContext(), R.string.error_product_not_valid, Toast.LENGTH_SHORT).show()
    }

    override fun errorSaveProduct() {
        Toast.makeText(requireContext(), R.string.error_product_not_found, Toast.LENGTH_SHORT).show()
    }

    private fun handleKeyboarEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_GO == actionId) {
            val product = save()
            if (product != null) {
                if (activity is onProductSavedListener) {
                    val listener = activity as onProductSavedListener
                    listener.onProductSaved(product)
                }
                dialog.dismiss()
                return true
            }
        }
        return false
    }

    private fun save(): Product? {
        val product = Product()
        product.id = arguments?.getLong(EXTRA_PRODUCT_ID, 0)?.toInt() ?: 0
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

    private fun convertValue(valueToText: String): BigDecimal {
        return try {
            BigDecimal(valueToText)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Erro no valor inserido.", Toast.LENGTH_LONG)
                    .show()
            BigDecimal.ZERO
        }
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
    }

    companion object {
        private const val DIALOG_TAG = "edit_dialog"
        private const val EXTRA_PRODUCT_ID = "product_id"
    }

}