package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.UpdateProductTask
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.dialog.AddProductDialog
import com.mithril.mobilegoldenleaf.ui.product.dialog.AlterProductDialog
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductListPresenter
import kotlinx.android.synthetic.main.activity_products_list.*

private const val TITLE = "Lista de Produtos"

class ProductListFragment : Fragment(), ProductListView {
    private val activityView by lazy { activity?.window?.decorView as ViewGroup }
    private val adapter by lazy { ProductAdapter(requireContext()) }
    private val presenter = ProductListPresenter(this,
            MobileGoldenLeafDataBase.getInstance(requireContext()).productRepository)

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProducts(all: List<Product>) {
        adapter.update(all)
    }

    override fun showProductDetails(product: Product) {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboad, null)
        configureList()
        configFba()
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.searchProducts("", adapter)
    }

    private fun configFba() {
        activity_products_list_fab_new_product.setOnClickListener {
            //callAddingDialog()
        }
    }

    private fun updateProducts() {

    }

    private fun configureList() {
        with(product_list_listview) {
            setOnItemClickListener { _, _, position, _ ->
                val product = adapter.getItem(position)
                //callEditingDialog(product as Product)
            }

        }
    }

//    private fun callEditingDialog(product: Product) {
//        AlterProductDialog(activityView, this)
//                .call(product, ProductDelegate { product ->
//                    UpdateProductTask(dao, product).execute()
//                    updateProducts()
//                })
//    }
//
////    private fun callAddingDialog() {
////        AddProductDialog(activityView, this)
////                .call(ProductDelegate { product ->
////                    SaveProductTask(dao, product).execute()
////                    updateProducts()
////                })
////    }

}