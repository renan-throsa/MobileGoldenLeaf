package com.mithril.mobilegoldenleaf.asynctask.client

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.repository.ClientRepository

class GetClientTask(private val repository: ClientRepository): AsyncTask<Unit, Unit, List<Client>>() {
    override fun doInBackground(vararg p0: Unit?): List<Client> {
        return repository.all()
    }
}