package com.mithril.mobilegoldenleaf.asynctask.address

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.persistence.repository.AddressRepository

class SaveAddressTask(private val repository: AddressRepository, private val address: Address) : AsyncTask<Unit, Unit, Long>() {
    override fun doInBackground(vararg p0: Unit?): Long {
        return repository.save(address)
    }
}