package com.mithril.mobilegoldenleaf.ui.client

import com.mithril.mobilegoldenleaf.models.Address

class AddressValidator {

    fun validate(a: Address) = with(a) {
        checkZipCode(zipCode) && checkStreet(street)
    }

    private fun checkZipCode(zipCode: String?) = zipCode?.length == 8
    private fun checkStreet(street: String?) = street?.length in 10..50

}