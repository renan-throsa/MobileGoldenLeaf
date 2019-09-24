package com.mithril.mobilegoldenleaf.ui.client.interfaces

import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.models.Client

interface ClientFormView {
    fun showClient(client: Client, address: Address)
    fun clientInvalidError()
    fun savingClientError()
    fun savingAddressError()
    fun addressInvalidError()
}