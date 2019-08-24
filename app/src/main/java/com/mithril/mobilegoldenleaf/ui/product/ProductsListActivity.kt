package com.mithril.mobilegoldenleaf.ui.product

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.UpdateProductTask
import com.mithril.mobilegoldenleaf.database.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.ui.category.dialog.AddCategoryDialog
import com.mithril.mobilegoldenleaf.ui.category.dialog.AlterCategoryDialog
import com.mithril.mobilegoldenleaf.ui.product.dialog.AddProductDialog
import com.mithril.mobilegoldenleaf.ui.product.dialog.AlterProductDialog
import kotlinx.android.synthetic.main.activity_products_list.*

private const val TITLE = "Lista de Produtos"

class ProductsListActivity : AppCompatActivity() {

    private val dao by lazy { MobileGoldenLeafDataBase.getInstance(this).productDao }
    private val activityView by lazy { window.decorView as ViewGroup }
    private val adapter by lazy { ProductAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)
        title = TITLE
        configureList()
        configFba()

    }

    override fun onResume() {
        super.onResume()
        updateProducts()
    }

    private fun configFba() {
        activity_products_list_fab_new_product.setOnClickListener {
            callAddingDialog()
        }
    }

    private fun updateProducts() {
        GetProductTask(dao, adapter).execute()
    }

    private fun configureList() {
        with(product_list_listview) {
            setOnItemClickListener { _, _, position, _ ->
                val product = adapter.getItem(position)
                callEditingDialog(product as Product)
            }

        }
    }

    private fun callEditingDialog(product: Product) {
        AlterProductDialog(activityView, this)
                .call(product, ProductDelegate { product ->
                    UpdateProductTask(dao, product).execute()
                    updateProducts()
                })
    }

    private fun callAddingDialog() {
        AddProductDialog(activityView, this)
                .call(ProductDelegate { product ->
                    SaveProductTask(dao, product).execute()
                    updateProducts()
                })
    }

}
