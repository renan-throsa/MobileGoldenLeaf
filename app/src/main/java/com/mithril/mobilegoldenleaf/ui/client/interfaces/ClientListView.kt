package com.mithril.mobilegoldenleaf.ui.client.interfaces

import com.mithril.mobilegoldenleaf.models.Client

interface ClientListView {
    fun showProgress()
    fun hideProgress()
    fun showClients(all: List<Client>)
    fun showOrdersOf(client: Client)

}