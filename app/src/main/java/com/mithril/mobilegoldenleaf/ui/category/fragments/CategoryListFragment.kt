package com.mithril.mobilegoldenleaf.ui.category.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.category.dialogs.CategoryFormDialog
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnProductsFromCategoryListener
import com.mithril.mobilegoldenleaf.ui.category.presenters.CategoryListPresenter
import kotlinx.android.synthetic.main.fragment_category_list.view.*

class CategoryListFragment : Fragment(), CategoryListView {

    private lateinit var activityContext: MainActivity

    private val adapter by lazy { CategoryAdapter(activityContext) }

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(activityContext).categoryRepository
        CategoryListPresenter(this, repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
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


    override fun onResume() {
        super.onResume()
        presenter.searchCategories("")
    }

    private fun addProductIn(category: Category) {
        val dialogFragment = ProductFormDialogFragment.newInstance(category.id)
        activityContext.supportFragmentManager.let { it -> dialogFragment.open(it) }

    }


    override fun showCategories(all: List<Category>) {
        val contexto = context
        val texto = "Quantidade " + all.size
        val duracao = Toast.LENGTH_SHORT
        val toast = Toast.makeText(contexto, texto, duracao)
        toast.show()

        adapter.update(all)

    }

    private fun configFba(view: View) {
        view.fragment_category_list_fab_new_category.setOnClickListener {
            CategoryFormDialog(activityContext, activityContext.window.decorView as ViewGroup)
                    .show { createdCategory ->
                        onDataBaseChanged(createdCategory)
                    }
        }
    }

    private fun openEditCategoryDialogFragment(category: Category) {
        CategoryFormDialog(activityContext, activityContext.window.decorView as ViewGroup, category.id)
                .show { alteredCategory ->
                    onDataBaseChanged(alteredCategory)
                }
    }

    private fun onDataBaseChanged(category: Category) {
        presenter.searchCategories("")
        val text = "Banco de dados atualizado com " + category.title
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun configureList(view: View) {
        with(view) {
            category_list.adapter = adapter
        }

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val category = adapter.getItem(item.groupId)
        when (item.itemId) {
            R.id.category_list_menu_edit -> openEditCategoryDialogFragment(category)
            R.id.category_list_menu_see_products -> openSeeProductsListFragment(category)
            R.id.category_list_menu_add_product -> addProductIn(category)
        }
        return super.onContextItemSelected(item)

    }


    private fun initFooter(): TextView {
        val txtFooter = TextView(context)
        txtFooter.text = resources.getQuantityString(R.plurals.footer_text_category,
                adapter.itemCount, adapter.itemCount)
        txtFooter.setBackgroundColor(Color.LTGRAY)
        txtFooter.gravity = Gravity.END
        txtFooter.setPadding(0, 8, 8, 8)
        return txtFooter
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


    override fun showProductsOf(category: Category) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun gettingCategoriesError() {
        val toast = Toast.makeText(context, R.string.getting_categories_error, Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {
        const val TAG_PRODUCT_LIST = "tagListaCategoria"
        fun newInstance(): CategoryListFragment {
            return CategoryListFragment()
        }
    }


}