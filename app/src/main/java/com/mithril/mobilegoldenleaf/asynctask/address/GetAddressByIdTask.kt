package com.mithril.mobilegoldenleaf.asynctask.address

import android.os.AsyncTask
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.persistence.repository.AddressRepository

class GetAddressByIdTask(private val addressId: Long, private val repository: AddressRepository) : AsyncTask<Unit, Unit, Address>() {
    override fun doInBackground(vararg p0: Unit?): Address {
        return repository.get(addressId)
    }
}