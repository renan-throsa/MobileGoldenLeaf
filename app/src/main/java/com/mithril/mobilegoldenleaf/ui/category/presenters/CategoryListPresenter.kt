package com.mithril.mobilegoldenleaf.ui.category.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryLocallyTask
import com.mithril.mobilegoldenleaf.asynctask.category.SaveListOfCategoriesLocallyTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.RetrofitInitializer
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListPresenter(private val view: CategoryListView,
                            private val repository: CategoryRepository) {


    fun searchCategories(term: String) {
        if (term.isEmpty()) {
            //The faster task first
            view.showCategories(GetCategoryLocallyTask(repository).execute().get())
            //The slower task last
            getCategoriesRemotely()
        } else {
            view.showCategories(repository.search(term))
        }
    }

    private fun getCategoriesRemotely() {
        val service = RetrofitInitializer().categoryService()
        val call = service.getAll()
        call.enqueue(object : Callback<List<Category>?> {
            override fun onResponse(call: Call<List<Category>?>, response: Response<List<Category>?>) {
                response.body()?.let {
                    val categories: List<Category> = it
                    view.showCategories(categories)
                    SaveListOfCategoriesLocallyTask(repository, categories)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

                }
            }

            override fun onFailure(call: Call<List<Category>?>, t: Throwable?) {
                view.gettingCategoriesError()
            }
        })
    }


}