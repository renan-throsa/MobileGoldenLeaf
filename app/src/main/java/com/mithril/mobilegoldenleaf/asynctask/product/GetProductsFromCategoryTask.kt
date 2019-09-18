package com.mithril.mobilegoldenleaf.asynctask.product

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository

class GetProductsFromCategoryTask(private val id: Long, private val repository: ProductRepository) : AsyncTask<Unit, Unit, List<Product>>() {
    override fun doInBackground(vararg p0: Unit?): List<Product> {
        return repository.productsWith(id)
    }


}