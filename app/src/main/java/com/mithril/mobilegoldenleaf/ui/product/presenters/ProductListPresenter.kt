package com.mithril.mobilegoldenleaf.ui.product.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductsFromCategoryTask
import com.mithril.mobilegoldenleaf.asynctask.product.SaveListOfProductsLocallyTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.retrofit.RetrofitInitializer
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListPresenter(private val view: ProductListView,
                           private val repository: ProductRepository) {

    fun searchProducts(term: String) {
        if (term.isEmpty()) {
            //The faster task first
            view.showProducts(GetProductTask(repository).execute().get())
            //The slower task last
            getCategoriesRemotely()
        } else {
            view.showProducts(repository.search(term))
        }
    }

    fun searchProductsWith(categoryId: Long) {
        view.showProducts(GetProductsFromCategoryTask(categoryId, repository).execute().get())
    }


    private fun getCategoriesRemotely() {
        val service = RetrofitInitializer().productService()
        val call = service.getAll()
        call.enqueue(object : Callback<List<Product>?> {
            override fun onResponse(call: Call<List<Product>?>, response: Response<List<Product>?>) {
                response.body()?.let {
                    val products: List<Product> = it
                    view.showProducts(products)
                    SaveListOfProductsLocallyTask(repository, products)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

                }
            }

            override fun onFailure(call: Call<List<Product>?>, t: Throwable?) {
                view.gettingProductsError()
            }
        })
    }


}