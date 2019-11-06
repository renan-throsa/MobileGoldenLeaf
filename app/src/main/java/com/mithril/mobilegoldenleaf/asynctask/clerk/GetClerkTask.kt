package com.mithril.mobilegoldenleaf.asynctask.clerk

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.persistence.repository.ClerkRepository

class GetClerkTask(private val email: String, private val password: String, private val repository: ClerkRepository) : AsyncTask<Unit, Unit, Clerk?>() {
    override fun doInBackground(vararg p0: Unit?): Clerk? {
        val clerk = repository.get(email)
        return if (clerk != null && clerk.password == password) {
            clerk
        } else {
            null
        }
    }

}
