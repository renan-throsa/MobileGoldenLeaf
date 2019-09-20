package com.mithril.mobilegoldenleaf.ui.client.validators

import com.mithril.mobilegoldenleaf.models.Client

class ClientValidator {

    fun validate(c: Client) = with(c) {
        checkIdentification(identification) && checkPhoneNumber(phoneNumber)
    }

    private fun checkIdentification(description: String?) = description?.length in 3..50
    private fun checkPhoneNumber(code: String?) = code?.length in 9..13

}