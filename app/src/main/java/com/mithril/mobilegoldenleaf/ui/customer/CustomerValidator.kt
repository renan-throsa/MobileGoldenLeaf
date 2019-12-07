package com.mithril.mobilegoldenleaf.ui.customer

import com.mithril.mobilegoldenleaf.models.Customer

class CustomerValidator {

    var error = "Everything is ok."

    fun validate(c: Customer): Boolean {
        with(c)
        {
            if (!checkIdentification(identification)) {
                error = "Erro. O número de identificação precisa ter 9 dígitos"
                return false
            }
            if (!checkPhoneNumber(phoneNumber)) {
                error = "Erro. O número de telefone precisa ter 11 dígitos"
                return false
            }

            if (!checkName(name)) {
                error = "Erro. O código precisa ter entre 9 e 13 caracteres."
                return false
            }

            return true

        }
    }

    private fun checkIdentification(identification: String?) = identification?.length == 9
    private fun checkPhoneNumber(number: String?) = number?.length == 11
    private fun checkName(name: String?) = name?.length in 4..50

}