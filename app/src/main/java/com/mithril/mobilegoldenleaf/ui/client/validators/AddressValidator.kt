package com.mithril.mobilegoldenleaf.ui.client.validators

import com.mithril.mobilegoldenleaf.models.Address

class AddressValidator {

    fun validate(a: Address) = with(a) {
        checkZipCode(zipCode) && checkStreet(street)
    }

    private fun checkZipCode(description: String?) = description?.length == 8
    private fun checkStreet(code: String?) = code?.length in 10..50

}