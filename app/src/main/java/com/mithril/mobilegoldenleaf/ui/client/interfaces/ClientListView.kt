package com.mithril.mobilegoldenleaf.ui.client.interfaces

import com.mithril.mobilegoldenleaf.models.Customer

interface ClientListView {
    fun showProgress()
    fun hideProgress()
    fun showClients(all: List<Customer>)
    fun showOrdersOf(customer: Customer)

}