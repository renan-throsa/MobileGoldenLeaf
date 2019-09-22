package com.mithril.mobilegoldenleaf.asynctask.client

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.repository.ClientRepository

class SaveClientTask(private val repository: ClientRepository, private val client: Client) : AsyncTask<Unit, Unit, Long>() {
    override fun doInBackground(vararg p0: Unit?): Long {
        return repository.save(client)
    }
}