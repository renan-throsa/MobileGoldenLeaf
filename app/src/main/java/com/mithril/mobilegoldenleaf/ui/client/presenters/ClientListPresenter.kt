package com.mithril.mobilegoldenleaf.ui.client.presenters

import com.mithril.mobilegoldenleaf.asynctask.client.GetClientTask
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerRepository
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientListView

class ClientListPresenter(private val view: ClientListView,
                          private val repository: CustomerRepository) {

    fun searchClient(term: String) {
        if (term.isEmpty()) {
            view.showClients(GetClientTask(repository).execute().get())
        } else {
            view.showClients(repository.search(term))
        }
    }
}