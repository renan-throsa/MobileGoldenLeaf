package com.mithril.mobilegoldenleaf.asynctask.category

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.persistence.repository.CategoryRepository
import com.mithril.mobilegoldenleaf.models.Category

class GetCategoryTask(private val repository: CategoryRepository) : AsyncTask<Unit, Unit, List<Category>>() {

    override fun doInBackground(vararg p0: Unit?): List<Category> {
        return repository.all()
    }


}
