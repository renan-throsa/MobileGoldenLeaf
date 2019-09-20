package com.mithril.mobilegoldenleaf.ui.client.presenters

import com.mithril.mobilegoldenleaf.asynctask.client.GetClientByIdTask
import com.mithril.mobilegoldenleaf.asynctask.client.SaveClientTask
import com.mithril.mobilegoldenleaf.asynctask.client.UpdateClientTask
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientFormView
import com.mithril.mobilegoldenleaf.ui.client.validators.ClientValidator

class ClientFormDialogPresenter(private val view: ClientFormView, private val repository: MobileGoldenLeafDataBase) {

    private val validator = ClientValidator()

    fun loadBy(clientId: Long) {
        val c: Client = GetClientByIdTask(clientId, repository.clientRepository).execute().get()
        view.showClient(c)
    }

    fun save(client: Client): Boolean {
        return if (validator.validate(client)) {
            try {
                if (client.id != 0L) {
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
}