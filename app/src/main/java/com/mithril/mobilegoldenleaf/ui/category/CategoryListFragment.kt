package com.mithril.mobilegoldenleaf.ui.category

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnProductsFromCategoryListener
import com.mithril.mobilegoldenleaf.ui.category.fragments.ProductFormDialogFragment
import com.mithril.mobilegoldenleaf.ui.product.ProductFormDialog
import kotlinx.android.synthetic.main.fragment_category_list.view.*

class CategoryListFragment : Fragment() {

    private lateinit var activityContext: MainActivity

    private val presenter by lazy {
        CategoryPresenter(AppDataBase.getInstance(activityContext).categoryRepository)
    }

    private val adapter by lazy { CategoryAdapter(activityContext) }


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
        searchCategories()
    }


    override fun onResume() {
        super.onResume()
        searchCategories()
    }

    private fun searchCategories() {
        presenter.get(
                whenSucceeded = {
                    adapter.update(it)
                }, whenFailed = {
            val toast = Toast.makeText(context, R.string.getting_categories_error, Toast.LENGTH_SHORT)
            toast.show()
        }
        )
    }

    private fun addProductIn(c: Category) {
        ProductFormDialog(activityContext, activityContext.window.decorView as ViewGroup, categoryId = c.id)
                .show(whenSucceeded = { productSaved ->
                    onDataBaseChanged(productSaved)
                }, whenFailed = { errorMessage ->
                    val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                    toast.show()
                }

                )
    }


    private fun configFba(view: View) {
        view.fragment_category_list_fab_new_category.setOnClickListener {
            CategoryFormDialog(activityContext, activityContext.window.decorView as ViewGroup)
                    .show(
                            whenSucceeded = { createdCategory ->
                                onDataBaseChanged(createdCategory)
                            }, whenFailed = { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                                .show()
                    }
                    )
        }
    }

    private fun openEditCategoryDialog(category: Category) {
        CategoryFormDialog(activityContext, activityContext.window.decorView as ViewGroup, category.id)
                .show(
                        whenSucceeded = { createdCategory ->
                            onDataBaseChanged(createdCategory)
                        }, whenFailed = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                            .show()
                }
                )
    }

    private fun onDataBaseChanged(category: Category) {
        searchCategories()
        val text = "Banco de dados atualizado com " + category.title
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun onDataBaseChanged(product: Product) {
        val text = "Banco de dados atualizado com " + product.description
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
            R.id.category_list_menu_edit -> openEditCategoryDialog(category)
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


    companion object {
        const val TAG_PRODUCT_LIST = "tagListaCategoria"
        fun newInstance(): CategoryListFragment {
            return CategoryListFragment()
        }
    }


}