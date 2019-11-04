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
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.product.interfaces.OnProductSavedListener
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView
import com.mithril.mobilegoldenleaf.ui.product.presenters.ProductListPresenter
import kotlinx.android.synthetic.main.fragment_products_list.view.*


class ProductListFragment : Fragment(), ProductListView, OnProductSavedListener {
    private lateinit var activityContext: MainActivity
    private val adapter by lazy { ProductAdapter(activityContext) }
    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(activityContext).productRepository
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

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.product_list_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val product = adapter.getItem(menuInfo.position)
        when (item.itemId) {
            R.id.product_list_menu_edit -> openEditProductDialogFragment(product)

        }
        return super.onContextItemSelected(item)

    }

    private fun openEditProductDialogFragment(product: Product) {
        val dialogFragment = ProductFormDialogFragment.newInstance(product.id)
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
    }


    private fun configFba(view: View) {
        view.fragment_products_list_fab_new_product
                .setOnClickListener {
                    val dialogFragment = ProductFormDialogFragment.newInstance()
                    activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
                }
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


    override fun showProductDetails(product: Product) {
        if (activity is OnProductClikedListener) {
            val listener = activity as OnProductClikedListener
            listener.onProductClick(product)
        }
    }

    override fun onProductSaved() {
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
        private const val EXTRA_CATEGORY_ID = "categoryId"
        private const val TAG_PRODUCT_LIST = "tagProductsList"

        fun newInstance(categoryId: Long = 0L): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle()
            args.putLong(EXTRA_CATEGORY_ID, categoryId)
            fragment.arguments = args
            return fragment
        }
    }


}