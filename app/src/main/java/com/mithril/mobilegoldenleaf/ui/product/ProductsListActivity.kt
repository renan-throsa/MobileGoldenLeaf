package com.mithril.mobilegoldenleaf.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductListAdapter
import com.mithril.mobilegoldenleaf.database.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.ui.product.GoldenLeafConstants.Companion.PRODUCT_KEY
import com.mithril.mobilegoldenleaf.ui.product.dialog.AddProductDialog
import com.mithril.mobilegoldenleaf.ui.product.dialog.AlterProductDialog

private const val TITLE = "Lista de Produtos"

class ProductsListActivity : AppCompatActivity() {

    private val dao by lazy {
        MobileGoldenLeafDataBase.getInstance(this).productDao
    }
    private val activityView by lazy { window.decorView as ViewGroup }
    private val adapter by lazy { ProductListAdapter(this) }
    private val newProductFba = findViewById<FloatingActionButton>(R.id.activity_products_list_fab_new_product)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)
        title = TITLE
        configureList()


        newProductFba.setOnClickListener { startActivity(Intent(this@ProductsListActivity, FormProductActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        updateProducts()
    }

    private fun updateProducts() {
        adapter.update(dao.all())
    }

    private fun configureList() {
        val productList = findViewById<ListView>(R.id.product_list_listview)
        configureAdapter(productList)
        productList.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, _, position, _ ->
            val product = adapterView.getItemAtPosition(position) as Product
            val intent = Intent(this@ProductsListActivity, FormProductActivity::class.java)
            intent.putExtra(PRODUCT_KEY, product)
            startActivity(intent)
            true
        }

    }

    private fun configureAdapter(product_list: ListView) {
        product_list.adapter = adapter
    }

    private fun callChangingDialog(product: Product, position: Int) {
        AlterProductDialog(activityView, this)
                .chama(product, ProductDelegate { transacao ->
                    //transacoes[position] = transacao
                    updateProducts()
                })
    }

    private fun callAddingDialog() {
        AddProductDialog(activityView, this)
                .chama(ProductDelegate { transacao ->
                    //transacoes.add(transacao)
                    updateProducts()
                    newProductFba.close(true)

                })
    }


}
