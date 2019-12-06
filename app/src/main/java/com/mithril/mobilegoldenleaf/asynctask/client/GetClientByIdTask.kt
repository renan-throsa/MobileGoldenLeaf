package com.mithril.mobilegoldenleaf.asynctask.client

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerRepository

class GetClientByIdTask(private val clientId: Long, private val repository: CustomerRepository) : AsyncTask<Unit, Unit, Customer>() {
    override fun doInBackground(vararg p0: Unit?): Customer {
        return repository.get(clientId)
    }
}