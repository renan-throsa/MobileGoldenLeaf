package com.mithril.mobilegoldenleaf.ui.category.fragments

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView
import com.mithril.mobilegoldenleaf.ui.category.presenters.CategoryListPresenter
import kotlinx.android.synthetic.main.fragment_category_list.view.*

class CategoryListFragment : Fragment(), CategoryListView {

    private val adapter by lazy { CategoryAdapter(requireContext()) }
    private val presenter = CategoryListPresenter(this,
            MobileGoldenLeafDataBase.getInstance(requireContext()).categoryRepository)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, null)
        configureList(view)
        configFba(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.searchCategories("")
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.category_list_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        val category = adapter.getItem(menuInfo.position)
        when (item.itemId) {
            R.id.category_list_menu_edit -> openEditCategoryDialogFragment(category)
            R.id.category_list_menu_add -> openAddCategoryDialogFragment()
            R.id.category_list_menu_see_products -> null
        }
        return super.onContextItemSelected(item)

    }

    override fun onResume() {
        super.onResume()
        presenter.searchCategories("")
    }

    override fun showCategories(all: List<Category>) {
        adapter.update(all)
    }

    private fun configFba(view: View) {

        view.fragment_category_list_fab_new_category.setOnClickListener {
            val dialogFragment = CategoryFormFragment.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }

    private fun configureList(view: View) {
        registerForContextMenu(view.category_list)
        with(view.category_list) {
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