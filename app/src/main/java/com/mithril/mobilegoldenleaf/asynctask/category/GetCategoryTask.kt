package com.mithril.mobilegoldenleaf.asynctask.category

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.adapters.CategoryAdapter
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao
import com.mithril.mobilegoldenleaf.models.Category

class GetCategoryTask(private val dao: CategoryDao, private val adapter: CategoryAdapter) : AsyncTask<Unit, Unit, List<Category>>() {

    override fun doInBackground(vararg p0: Unit?): List<Category> {
        return dao.all()
    }

    override fun onPostExecute(result: List<Category>) {
        super.onPostExecute(result)
        adapter.update(result)
    }


}
