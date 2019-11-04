package com.mithril.mobilegoldenleaf.asynctask.category

import android.os.AsyncTask
import android.util.Log
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.models.Category

class SaveListOfCategoriesLocallyTask(private val repository: CategoryRepository, private val categories: List<Category>)
    : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        repository.save(categories)
    }

}
