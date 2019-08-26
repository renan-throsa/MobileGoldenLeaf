package com.mithril.mobilegoldenleaf.asynctask.product

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.models.Product

class UpdateProductTask(private val repository: ProductRepository, private val product: Product) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        repository.update(product)
    }

}
