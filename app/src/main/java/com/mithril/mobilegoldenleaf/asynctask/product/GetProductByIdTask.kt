package com.mithril.mobilegoldenleaf.asynctask.product

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository

class GetProductByIdTask(private val id: Long, private val repository: ProductRepository) : AsyncTask<Unit, Unit, Product>() {

    override fun doInBackground(vararg p0: Unit?): Product {
        return repository.get(id)
    }


}