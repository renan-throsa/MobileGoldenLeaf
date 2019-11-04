package com.mithril.mobilegoldenleaf.ui.category.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryLocallyTask
import com.mithril.mobilegoldenleaf.asynctask.category.SaveListOfCategoriesLocalyTask
import com.mithril.mobilegoldenleaf.asynctask.remote.GetCategoryRemotelyTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.RetrofitInitializer
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryListView

class CategoryListPresenter(private val view: CategoryListView,
                            private val repository: CategoryRepository) {


    fun searchCategories(term: String) {
        if (term.isEmpty()) {
            view.showCategories(GetCategoryLocallyTask(repository).execute().get())
            val categories = getCategoriesRemotely()
            if (categories != null) {
                SaveListOfCategoriesLocalyTask(repository, categories)

            } else {
                view.gettingCategoriesError()
            }

        } else {
            view.showCategories(repository.search(term))
        }
    }

    private fun getCategoriesRemotely(): List<Category>? {
        val service = RetrofitInitializer().categoryService()
        val call = service.getAll()
        val response = GetCategoryRemotelyTask(call)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                .get()
        return response.body()
    }


}