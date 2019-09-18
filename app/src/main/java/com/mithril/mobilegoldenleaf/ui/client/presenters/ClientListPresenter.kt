package com.mithril.mobilegoldenleaf.ui.client.presenters

import com.mithril.mobilegoldenleaf.asynctask.client.GetClientTask
import com.mithril.mobilegoldenleaf.persistence.repository.ClientRepository
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientListView

class ClientListPresenter(private val view: ClientListView,
                          private val repository: ClientRepository) {

    fun searchClient(term: String) {
        if (term.isEmpty()) {
            view.showClients(GetClientTask(repository).execute().get())
        } else {
            view.showClients(repository.search(term))
        }
    }
}