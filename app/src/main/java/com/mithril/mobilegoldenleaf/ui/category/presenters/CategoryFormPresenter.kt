package com.mithril.mobilegoldenleaf.ui.category.presenters

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryByIdTask
import com.mithril.mobilegoldenleaf.asynctask.category.SaveCategoryTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.retrofit.RetrofitInitializer
import com.mithril.mobilegoldenleaf.ui.category.interfaces.CategoryFormView
import com.mithril.mobilegoldenleaf.ui.category.validators.CategoryValidator
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CategoryFormPresenter(private val view: CategoryFormView,
                            private val repository: CategoryRepository) {

    fun loadBy(categoryId: Long) {
        val category = GetCategoryByIdTask(categoryId, repository).execute().get()
        view.show(category)
    }

    fun save(category: Category): Boolean {
        var status = false
        if (CategoryValidator().validate(category)) {
            val service = RetrofitInitializer().categoryService()
            val call = service.save(category)
            call.enqueue(object : Callback<Category?> {
                override fun onResponse(call: Call<Category?>, response: Response<Category?>) {
                    response.body()?.let {
                        status = true
                    }
                }

                override fun onFailure(call: Call<Category?>, t: Throwable) {
                    view.showSavingCategoryError()
                    status = false
                }


            })
            SaveCategoryTask(repository, category)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)


        } else {
            view.showInvalidTitleError()
            status = false
        }
        return status
    }

}