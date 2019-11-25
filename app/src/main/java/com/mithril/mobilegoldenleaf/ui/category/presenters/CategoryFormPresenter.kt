package com.mithril.mobilegoldenleaf.ui.category.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryByIdTask
import com.mithril.mobilegoldenleaf.asynctask.category.SaveCategoryTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryFormView
import com.mithril.mobilegoldenleaf.ui.category.validators.CategoryValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFormPresenter(private val view: CategoryFormView,
                            private val repository: CategoryRepository) {

    fun loadBy(categoryId: Long) {
        val category = GetCategoryByIdTask(categoryId, repository).execute().get()
        view.show(category)
    }

    fun save(category: Category): Boolean {
        return if (CategoryValidator().validate(category)) {
            val service = AppRetrofit().categoryService()
            val call = if (category.id != 0L) {
                service.update(category.id, category)
            } else {
                service.save(category)
            }
            call.enqueue(object : Callback<Category?> {
                override fun onResponse(call: Call<Category?>, response: Response<Category?>) {
                    response.body()?.let {
                    }
                }

                override fun onFailure(call: Call<Category?>, t: Throwable) {
                    view.showSavingCategoryError()

                }


            })
            SaveCategoryTask(repository, category)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            true

        } else {
            view.showInvalidTitleError()
            false
        }

    }

}