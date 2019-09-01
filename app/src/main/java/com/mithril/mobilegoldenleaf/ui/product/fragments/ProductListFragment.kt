package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.interfaces.OnProductClikedListener
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductListPresenter
import kotlinx.android.synthetic.main.fragment_products_list.*

private const val TITLE = "Lista de Produtos"

class ProductListFragment : Fragment(), ProductListView, OnProductSavedListener {

    private val activityView by lazy { activity?.window?.decorView as ViewGroup }
    private val adapter by lazy { ProductAdapter(requireContext()) }
    private val presenter = ProductListPresenter(this,
            MobileGoldenLeafDataBase.getInstance(requireContext()).productRepository)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_products_list, null)
        configureList()
        configFba()
        return view
    }

    private fun configFba() {
        activity_products_list_fab_new_product.setOnClickListener {
            val dialogFragment = ProductFormFragment.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.searchProducts("", adapter)
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


    override fun showProductDetails(product: Product) {
        if (activity is OnProductClikedListener) {
            val listener = activity as OnProductClikedListener
            listener.onProductClick(product)
        }
    }

    override fun onProductSaved(product: Product) {
        presenter.searchProducts("", adapter)
    }

    private fun configureList() {
        with(product_list) {
            setOnItemClickListener { _, _, position, _ ->
                val product = adapter.getItem(position) as Product
                presenter.showProductDetails(product)
            }

        }
    }

    companion object {
        const val TAG_PRODUCT_LIST = "tagListaProduto"
        fun newInstance(id: Long = 0): ProductListFragment {
            return ProductListFragment()
        }
    }


}