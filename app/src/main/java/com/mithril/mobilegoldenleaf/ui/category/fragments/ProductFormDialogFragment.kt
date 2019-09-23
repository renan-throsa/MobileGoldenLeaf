package com.mithril.mobilegoldenleaf.ui.category.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.category.interfaces.ProductFormDialogView
import com.mithril.mobilegoldenleaf.ui.category.presenters.ProductFormDialogPresenter
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import kotlinx.android.synthetic.main.dialogfragment_product_form.*
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition
import java.util.*

class ProductFormDialogFragment : DialogFragment(), ProductFormDialogView {
    private lateinit var activityContext: MainActivity

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(activityContext)
        ProductFormDialogPresenter(this, repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialogfragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        * Here ProductFormDialogFragment will always be attached to a Category. Thus, is not
        * necessary verify if the id is null*/
        val categoryId = arguments?.getLong(EXTRA_CATEGORY_ID)!!
        presenter.loadBy(categoryId)
        dialog.setTitle(R.string.add_product)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_action_concluded_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.CustomDialogFragment)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.fragment_form_action_concluded) {
            val product = saveProduct()
            if (product != null) {
                if (activity is OnProductSavedListener) {
                    val listener = activity as OnProductSavedListener
                    listener.onProductSaved()
                }
            }
            dialog.dismiss()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showCategory(c: Category) {
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listOf(c)) }
        adapter?.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        form_category_spinner.adapter = adapter
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
        product.categoryId = arguments?.getLong(EXTRA_CATEGORY_ID)!!
        product.brand = form_product_brand.text.toString()
        product.description = form_product_description.text.toString()
        product.code = form_product_code.text.toString()
        product.unitCost = convertValue(form_product_value.text.toString())
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


    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
    }

    companion object {
        private const val DIALOG_TAG = "categoryId"
        private const val EXTRA_CATEGORY_ID = "formDialog"

        fun newInstance(id: Long): ProductFormDialogFragment {
            val fragment = ProductFormDialogFragment()
            val args = Bundle()
            args.putLong(EXTRA_CATEGORY_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}