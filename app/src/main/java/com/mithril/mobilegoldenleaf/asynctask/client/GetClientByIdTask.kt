package com.mithril.mobilegoldenleaf.asynctask.client

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.repository.ClientRepository

class GetClientByIdTask(private val clientId: Long, private val repository: ClientRepository) : AsyncTask<Unit, Unit, Client>() {
    override fun doInBackground(vararg p0: Unit?): Client {
        return repository.get(clientId)
    }
}