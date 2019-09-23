package com.mithril.mobilegoldenleaf.ui.client.validators

import com.mithril.mobilegoldenleaf.models.Client

class ClientValidator {

    fun validate(c: Client) = with(c) {
        checkIdentification(identification) && checkPhoneNumber(phoneNumber)
    }

    private fun checkIdentification(description: String?) = description?.length == 11
    private fun checkPhoneNumber(code: String?) = code?.length == 10

}