package com.mithril.mobilegoldenleaf.asynctask.clerk

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.persistence.repository.ClerkRepository

class SaveClerkTask(private val clerk: Clerk, private val repository: ClerkRepository) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        repository.save(clerk)
    }
}