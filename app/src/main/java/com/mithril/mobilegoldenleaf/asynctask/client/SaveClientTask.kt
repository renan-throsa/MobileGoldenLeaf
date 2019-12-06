package com.mithril.mobilegoldenleaf.asynctask.client

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerRepository

class SaveClientTask(private val repository: CustomerRepository, private val customer: Customer) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        repository.save(customer)
    }
}