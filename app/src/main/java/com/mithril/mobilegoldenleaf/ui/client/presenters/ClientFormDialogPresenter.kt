package com.mithril.mobilegoldenleaf.ui.client.presenters

import com.mithril.mobilegoldenleaf.asynctask.address.DeleteAddressTask
import com.mithril.mobilegoldenleaf.asynctask.address.GetAddressByIdTask
import com.mithril.mobilegoldenleaf.asynctask.address.SaveAddressTask
import com.mithril.mobilegoldenleaf.asynctask.address.UpdateAddressTask
import com.mithril.mobilegoldenleaf.asynctask.client.GetClientByIdTask
import com.mithril.mobilegoldenleaf.asynctask.client.SaveClientTask
import com.mithril.mobilegoldenleaf.asynctask.client.UpdateClientTask
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientFormView
import com.mithril.mobilegoldenleaf.ui.client.validators.AddressValidator
import com.mithril.mobilegoldenleaf.ui.client.validators.ClientValidator

class ClientFormDialogPresenter(private val view: ClientFormView, private val repository: AppDataBase) {

    private val clientValidator = ClientValidator()
    private val addressValidator = AddressValidator()

    fun loadBy(clientId: Long) {
        val c: Client = GetClientByIdTask(clientId, repository.clientRepository).execute().get()
        val a: Address = GetAddressByIdTask(c.addressId, repository.addressRepository).execute().get()
        view.showClient(c, a)
    }

    fun save(client: Client): Boolean {
        return if (clientValidator.validate(client)) {
            try {
                if (client.hasValidId()) {
                    UpdateClientTask(repository.clientRepository, client).execute()
                } else {
                    SaveClientTask(repository.clientRepository, client).execute()
                }
                true
            } catch (e: Exception) {
                view.savingClientError()
                false
            }
        } else {
            view.clientInvalidError()
            false
        }
    }

    fun save(address: Address): Boolean {
        return if (addressValidator.validate(address)) {
            try {
                if (address.hasValidId()) {
                    UpdateAddressTask(repository.addressRepository, address).execute()
                } else {
                    address.id = SaveAddressTask(repository.addressRepository, address).execute().get()
                }
                true
            } catch (e: Exception) {
                view.savingAddressError()
                false
            }
        } else {
            view.addressInvalidError()
            false
        }
    }

    fun delete(address: Address) {
        DeleteAddressTask(repository.addressRepository, address)
    }
}