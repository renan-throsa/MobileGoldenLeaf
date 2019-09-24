package com.mithril.mobilegoldenleaf.asynctask.address

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.persistence.repository.AddressRepository

class DeleteAddressTask(private val repository: AddressRepository, private val address: Address) : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg p0: Unit?) {
        repository.delete(address)
    }
}