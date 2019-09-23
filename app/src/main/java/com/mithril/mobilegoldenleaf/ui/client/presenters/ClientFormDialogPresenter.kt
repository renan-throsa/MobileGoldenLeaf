package com.mithril.mobilegoldenleaf.ui.client.presenters

import com.mithril.mobilegoldenleaf.asynctask.address.SaveAddressTask
import com.mithril.mobilegoldenleaf.asynctask.address.UpdateAddressTask
import com.mithril.mobilegoldenleaf.asynctask.client.GetClientByIdTask
import com.mithril.mobilegoldenleaf.asynctask.client.SaveClientTask
import com.mithril.mobilegoldenleaf.asynctask.client.UpdateClientTask
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientFormView
import com.mithril.mobilegoldenleaf.ui.client.validators.AddressValidator
import com.mithril.mobilegoldenleaf.ui.client.validators.ClientValidator

class ClientFormDialogPresenter(private val view: ClientFormView, private val repository: MobileGoldenLeafDataBase) {

    private val clientValidator = ClientValidator()
    private val addressValidator = AddressValidator()

    fun loadBy(clientId: Long) {
        val c: Client = GetClientByIdTask(clientId, repository.clientRepository).execute().get()
        view.showClient(c)
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
                    SaveAddressTask(repository.addressRepository, address).execute()
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
}