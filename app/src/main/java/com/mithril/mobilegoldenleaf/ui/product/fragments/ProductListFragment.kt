package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.product.dialogs.ProductFormDialog
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductListPresenter
import kotlinx.android.synthetic.main.fragment_products_list.view.*


class ProductListFragment : Fragment(), ProductListView {


    private lateinit var activityContext: MainActivity
    private val adapter by lazy { ProductAdapter(activityContext) }
    private val presenter by lazy {
        val repository = AppDataBase.getInstance(activityContext).productRepository
        ProductListPresenter(this, repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_products_list, null)
        configureList(view)
        configFba(view)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categoryId = arguments?.getLong(EXTRA_CATEGORY_ID, 0L) ?: 0L
        if (categoryId != 0L) {
            val contexto = activityContext
            val texto = "Category " + categoryId
            val duracao = Toast.LENGTH_SHORT

            val toast = Toast.makeText(contexto, texto, duracao)
            toast.show()

            presenter.searchProductsWith(categoryId)
        }
        presenter.searchProducts("")

    }

    private fun openEditProductDialogFragment(product: Product) {
        ProductFormDialog(activityContext, activityContext.window.decorView as ViewGroup
                , product.id).show { productEdited ->
            onDataBaseChanged(productEdited)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val product = adapter.getItem(item.groupId)
        when (item.itemId) {
            R.id.product_list_menu_edit -> openEditProductDialogFragment(product)

        }
        return super.onContextItemSelected(item)

    }

    private fun configFba(view: View) {
        view.fragment_products_list_fab_new_product
                .setOnClickListener {
                    ProductFormDialog(activityContext, activityContext.window.decorView as ViewGroup
                    ).show { productCreated ->
                        onDataBaseChanged(productCreated)
                    }
                }
    }

    private fun onDataBaseChanged(product: Product) {
        presenter.searchProducts("")
        val text = "Banco de dados atualizado com " + product.description
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()

    }

    override fun onResume() {
        super.onResume()
        presenter.searchProducts("")
    }

    override fun showProducts(all: List<Product>) {
        adapter.update(all)
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun gettingProductsError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun configureList(view: View) {
        with(view) {
            product_list.adapter = adapter
        }

    }


    companion object {
        private const val EXTRA_CATEGORY_ID = "categoryId"

        fun newInstance(categoryId: Long = 0L): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle()
            args.putLong(EXTRA_CATEGORY_ID, categoryId)
            fragment.arguments = args
            return fragment
        }
    }


}