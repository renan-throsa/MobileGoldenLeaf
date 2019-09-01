package com.mithril.mobilegoldenleaf.ui.category.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_products_list.activity_products_list_fab_new_product

class CategoryListFragment : ListFragment(), CategoryListView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, null)
        configureList()
        configFba()
        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.category_list_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        val category = listAdapter.getItem(menuInfo.position) as Category
        when (item.itemId) {
            R.id.category_list_menu_edit -> openEditCategoryDialogFragment(category)
            R.id.category_list_menu_add -> openAddCategoryDialogFragment()
            R.id.category_list_menu_see_products -> null
        }
        return super.onContextItemSelected(item)

    }

    private fun configFba() {
        activity_products_list_fab_new_product.setOnClickListener {
            val dialogFragment = CategoryFormFragment.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }

    private fun configureList() {
        registerForContextMenu(category_list)
        with(category_list) {
            setOnItemLongClickListener { _, _, position, _ ->
                val category = adapter.getItem(position)
                false
            }

        }

    }

    private fun openEditCategoryDialogFragment(category: Category) {
        val dialogFragment = CategoryFormFragment.newInstance()
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
    }

    private fun openAddCategoryDialogFragment() {
        val dialogFragment = CategoryFormFragment.newInstance()
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
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

    override fun showCategories(all: List<Category>) {
        val adapter = ArrayAdapter<Category>(requireContext(), android.R.layout.simple_list_item_1, all)
        listAdapter = adapter
    }

    override fun editCategoryDetails(category: Category) {

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