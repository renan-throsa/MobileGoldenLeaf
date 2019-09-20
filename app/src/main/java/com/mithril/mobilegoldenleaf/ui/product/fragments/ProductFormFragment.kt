package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.extentions.toDecimalFormat
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductFormPresenter
import kotlinx.android.synthetic.main.dialogfragment_product_form.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition
import java.util.*

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
        return inflater.inflate(R.layout.dialogfragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadCategories()
        val productId = arguments?.getLong(EXTRA_PRODUCT_ID, 0L) ?: 0L
        if (productId != 0L) {
            presenter.loadBy(productId)
            dialog.setTitle(R.string.edit_product)
        }

        dialog.setTitle(R.string.add_product)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_action_concluded_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.fragment_form_action_concluded) {
            val product = saveProduct()
            if (product != null) {
                if (activity is OnProductSavedListener) {
                    val listener = activity as OnProductSavedListener
                    listener.onProductSaved(product)
                }
            }
            dialog.dismiss()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun productInvalidError() {
        val toast = Toast.makeText(context, R.string.product_invalid_error, Toast.LENGTH_SHORT)
        toast.show()

    }

    override fun savingProductError() {
        val toast = Toast.makeText(context, R.string.product_saving_error, Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun saveProduct(): Product? {
        val product = Product()
        product.categoryId = getCategoryFromSpinner()
        product.brand = form_product_brand.text.toString()
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

    private fun getCategoryFromSpinner(): Long {
        val position = form_category_spinner.selectedItemPosition
        val category = form_category_spinner.adapter.getItem(position) as Category
        return category.id
    }

    override fun showProduct(product: Product) {
        form_category_spinner.setSelection(getIndex(form_category_spinner, product.categoryId))
        form_product_value.setText(product.unitCost.toDecimalFormat())
        form_product_brand.setText(product.brand)
        form_product_description.setText(product.description)
        form_product_code.setText(product.code)
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

    override fun showCategories(c: List<Category>) {
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, c) }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        form_category_spinner.adapter = adapter
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


    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
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