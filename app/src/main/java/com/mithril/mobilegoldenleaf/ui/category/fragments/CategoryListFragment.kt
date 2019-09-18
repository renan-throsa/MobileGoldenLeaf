package com.mithril.mobilegoldenleaf.ui.category.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnCategorySavedListener
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnProductsFromCategoryListener
import com.mithril.mobilegoldenleaf.ui.category.presenters.CategoryListPresenter
import kotlinx.android.synthetic.main.fragment_category_list.view.*

class CategoryListFragment : Fragment(), CategoryListView, OnCategorySavedListener {

    private val adapter by lazy {
        context.let {
            if (it != null) {
                CategoryAdapter(it)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }

    }

    private val presenter by lazy {
        context.let {
            if (it != null) {
                val repository = MobileGoldenLeafDataBase.getInstance(it).categoryRepository
                CategoryListPresenter(this, repository)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, null)
        configureList(view)
        configFba(view)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.searchCategories("")
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.category_list_menu, menu)
    }

    override fun onResume() {
        super.onResume()
        presenter.searchCategories("")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        val category = adapter.getItem(menuInfo.position)
        when (item.itemId) {
            R.id.category_list_menu_edit -> openEditCategoryDialogFragment(category)
            R.id.category_list_menu_see_products -> openSeeProductsListFragment(category)
            R.id.category_list_menu_add_product -> addProductIn(category)
        }
        return super.onContextItemSelected(item)

    }

    private fun addProductIn(category: Category) {
        val dialogFragment = ProductFormDialogFragment.newInstance(category.id)
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }

    }


    override fun showCategories(all: List<Category>) {
        val contexto = context
        val texto = "Quantidade " + all.size
        val duracao = Toast.LENGTH_SHORT
        val toast = Toast.makeText(contexto, texto, duracao)
        toast.show()

        adapter.update(all)

    }

    override fun onCategorySaved() {
        presenter.searchCategories("")
    }

    private fun configFba(view: View) {
        view.fragment_category_list_fab_new_category.setOnClickListener {
            val dialogFragment = CategoryFormFragment.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }

    private fun configureList(view: View) {
        with(view) {
            category_list.adapter = adapter
            registerForContextMenu(category_list)
            category_list.addFooterView(initFooter())
        }

    }

    private fun initFooter(): TextView {
        val txtFooter = TextView(context)
        txtFooter.text = resources.getQuantityString(R.plurals.footer_text_category, adapter.count, adapter.count)
        txtFooter.setBackgroundColor(Color.LTGRAY)
        txtFooter.gravity = Gravity.END
        txtFooter.setPadding(0, 8, 8, 8)
        return txtFooter
    }

    private fun openEditCategoryDialogFragment(category: Category) {
        val dialogFragment = CategoryFormFragment.newInstance(category.id)
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
    }

    private fun openSeeProductsListFragment(category: Category) {
        if (activity is OnProductsFromCategoryListener) {
            val listener = activity as OnProductsFromCategoryListener
            listener.OnProductsFromCategoryClick(category)
        }
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addProduct(product: Product) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showProductsOf(category: Category) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG_PRODUCT_LIST = "tagListaCategoria"
        fun newInstance(): CategoryListFragment {
            return CategoryListFragment()
        }
    }


}