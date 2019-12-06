package com.mithril.mobilegoldenleaf.ui.client.interfaces

import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.models.Customer

interface ClientFormView {
    fun showClient(customer: Customer, address: Address)
    fun clientInvalidError()
    fun savingClientError()
    fun savingAddressError()
    fun addressInvalidError()
}