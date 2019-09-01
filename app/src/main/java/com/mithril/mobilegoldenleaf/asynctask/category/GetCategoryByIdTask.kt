package com.mithril.mobilegoldenleaf.asynctask.category

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository

class GetCategoryByIdTask(private val categoryId: Long, private val repository: CategoryRepository) : AsyncTask<Unit, Unit, Category>() {
    override fun doInBackground(vararg p0: Unit?): Category {
        return repository.get(categoryId)
    }

}
