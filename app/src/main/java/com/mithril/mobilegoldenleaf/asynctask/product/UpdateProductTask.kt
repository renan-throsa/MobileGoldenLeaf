package com.mithril.mobilegoldenleaf.asynctask.product

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.database.dao.ProductDao
import com.mithril.mobilegoldenleaf.models.Product

class UpdateProductTask(private val dao: ProductDao, private val product: Product) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        dao.update(product)
    }

}
