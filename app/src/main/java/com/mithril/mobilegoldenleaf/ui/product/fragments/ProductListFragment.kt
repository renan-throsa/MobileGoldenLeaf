package com.mithril.mobilegoldenleaf.ui.product.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.interfaces.OnProductClikedListener
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductListPresenter
import kotlinx.android.synthetic.main.fragment_products_list.view.*


private const val TITLE = "Lista de Produtos"

class ProductListFragment : Fragment(), ProductListView, OnProductSavedListener {

    private val adapter by lazy {
        context.let {
            if (it != null) {
                ProductAdapter(it)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }
    }

    private val presenter by lazy {
        context.let {
            if (it != null) {
                val repository = MobileGoldenLeafDataBase.getInstance(it).productRepository
                ProductListPresenter(this, repository)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_products_list, null)
        configureList(view)
        configFba(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.searchProducts("")

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.product_list_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        val product = adapter.getItem(menuInfo.position)
        when (item.itemId) {
            R.id.product_list_menu_edit -> openEditProductDialogFragment(product)

        }
        return super.onContextItemSelected(item)

    }

    private fun openEditProductDialogFragment(product: Product) {
        val dialogFragment = ProductFormFragment.newInstance(product.id)
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
    }


    private fun configFba(view: View) {
        view.fragment_products_list_fab_new_product
                .setOnClickListener {
                    val dialogFragment = ProductFormFragment.newInstance()
                    activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
                }
    }

    override fun onResume() {
        super.onResume()
        presenter.searchProducts("")
    }

    override fun showProducts(all: List<Product>) {
        val contexto = context
        val texto = "Quantidade " + all.size
        val duracao = Toast.LENGTH_SHORT

        val toast = Toast.makeText(contexto, texto, duracao)
        toast.show()
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
        presenter.searchProducts("")
    }

    private fun configureList(view: View) {
        with(view) {
            product_list.adapter = adapter
            registerForContextMenu(product_list)
            product_list.addFooterView(initFooter())
        }

    }

    private fun initFooter(): TextView {
        val txtFooter = TextView(context)
        txtFooter.text = resources.getQuantityString(R.plurals.footer_text_product, adapter.count, adapter.count)
        txtFooter.setBackgroundColor(Color.LTGRAY)
        txtFooter.gravity = Gravity.END
        txtFooter.setPadding(0, 8, 8, 8)
        return txtFooter
    }

    companion object {
        const val TAG_PRODUCT_LIST = "tagListaProdutos"
        fun newInstance(): ProductListFragment {
            return ProductListFragment()
        }
    }


}