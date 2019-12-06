package com.mithril.mobilegoldenleaf.asynctask.client

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerRepository

class GetClientTask(private val repository: CustomerRepository): AsyncTask<Unit, Unit, List<Customer>>() {
    override fun doInBackground(vararg p0: Unit?): List<Customer> {
        return repository.all()
    }
}