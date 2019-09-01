package com.mithril.mobilegoldenleaf.asynctask.product

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.adapters.ProductAdapter
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.models.Product


class GetProductTask(private val repository: ProductRepository) : AsyncTask<Unit, Unit, List<Product>>() {
    override fun doInBackground(vararg p0: Unit?): List<Product> {
        return repository.all()
    }
}