package com.mithril.mobilegoldenleaf.ui.category.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryLocallyTask
import com.mithril.mobilegoldenleaf.asynctask.category.SaveListOfCategoriesLocallyTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.retrofit.webclient.CategoryWebClient
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListPresenter(private val view: CategoryListView,
                            private val repository: CategoryRepository) {

    private val webclient = CategoryWebClient()
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
        val service = AppRetrofit().categoryService()
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

    private fun searchInternaly(whenSuccess: (List<Category>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            repository.all()
        }, whenFinalize = whenSuccess)
                .execute()
    }

    private fun searchRemotely(
            whenSuccess: (List<Category>) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        webclient.getAll(
                whenSuccess = { newCategories ->
                    newCategories?.let {
                        salveInternaly(newCategories, whenSuccess)
                    }
                }, whenFail = whenFail
        )
    }

    private fun salveInternaly(
            categories: List<Category>,
            whenSuccess: (newCategories: List<Category>) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    repository.save(categories)
                    repository.all()
                }, whenFinalize = whenSuccess
        ).execute()
    }


}