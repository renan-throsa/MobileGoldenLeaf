package com.mithril.mobilegoldenleaf.asynctask.category

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao
import com.mithril.mobilegoldenleaf.models.Category

class SaveCategoryTask(private val dao: CategoryDao, private val category: Category) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        dao.save(category)
    }

}
