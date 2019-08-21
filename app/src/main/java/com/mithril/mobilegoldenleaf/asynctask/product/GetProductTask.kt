package com.mithril.mobilegoldenleaf.asynctask.product

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.database.dao.ProductDao
import com.mithril.mobilegoldenleaf.models.Product


class GetProductTask(private val dao: ProductDao, private val adapter: ProductAdapter) : AsyncTask<Unit, Unit, List<Product>>() {
    override fun doInBackground(vararg p0: Unit?): List<Product> {
        return dao.all()
    }


    override fun onPostExecute(result: List<Product>) {
        super.onPostExecute(result)
        adapter.update(result)
    }
}