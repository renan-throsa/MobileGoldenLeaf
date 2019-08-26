package com.mithril.mobilegoldenleaf.asynctask.category

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.models.Category

class SaveCategoryTask(private val repository: CategoryRepository, private val category: Category) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        repository.save(category)
    }

}
