package com.mithril.mobilegoldenleaf.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_products_list.view.*


class ProductListFragment : Fragment() {

    private lateinit var activityContext: MainActivity

    private val adapter by lazy { ProductAdapter(activityContext) }

    private val presenter by lazy {
        val repository = AppDataBase.getInstance(activityContext).productDao
        ProductRepository(repository)
    }

    private val viewModel by lazy {
        val repository = ProductRepository(AppDataBase.getInstance(activityContext).productDao)
        val factory = ProductViewModelFactory(repository)
        ViewModelProviders.of(this, factory).get(ProductViewModel::class.java)
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
            searchProductsWith(categoryId)
        }
        searchProducts()

    }

    private fun openEditProductDialogFragment(product: Product) {
        ProductFormDialog(activityContext, activityContext.window.decorView as ViewGroup
                , product.id).show(whenSucceeded = { productEdited ->
            onDataBaseChanged(productEdited)
        }, whenFailed = { errorMessage ->
            val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }
        )
    }



    private fun configFba(view: View) {
        view.fragment_products_list_fab_new_product
                .setOnClickListener {
                    ProductFormDialog(activityContext, activityContext.window.decorView as ViewGroup
                    ).show(whenSucceeded = { productSaved ->
                        onDataBaseChanged(productSaved)
                    }, whenFailed = { errorMessage ->
                        val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                        toast.show()
                    }

                    )
                }
    }

    private fun onDataBaseChanged(product: Product) {
        searchProducts()
        val text = "Banco de dados atualizado com " + product.description
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()

    }

    override fun onResume() {
        super.onResume()
        searchProducts()
    }


    private fun searchProducts() {
        viewModel.get(
                whenSucceeded = {
                    adapter.update(it)
                }, whenFailed = {
            val toast = Toast.makeText(context, R.string.getting_products_error, Toast.LENGTH_SHORT)
            toast.show()
        }
        )
    }

    private fun searchProductsWith(categoryId: Long) {
        viewModel.get(categoryId,
                whenSucceeded = {
                    adapter.update(it)
                }, whenFailed = {
            val toast = Toast.makeText(context, R.string.getting_products_error, Toast.LENGTH_SHORT)
            toast.show()
        }
        )
    }


    private fun configureList(view: View) {
        with(view) {
            product_list.adapter = adapter
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val product = adapter.getItem(item.groupId)
        when (item.itemId) {
            R.id.product_list_menu_edit -> openEditProductDialogFragment(product)
        }
        return super.onContextItemSelected(item)

    }


    companion object {
        private const val EXTRA_CATEGORY_ID = "categoryId"

        fun newInstance(categoryId: Long = 0L): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle()
            args.putLong(EXTRA_CATEGORY_ID, categoryId)
            fragment.arguments = args
            return fragment
        }
    }


}