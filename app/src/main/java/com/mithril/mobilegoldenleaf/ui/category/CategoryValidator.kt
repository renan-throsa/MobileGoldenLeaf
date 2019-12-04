package com.mithril.mobilegoldenleaf.ui.category

import com.mithril.mobilegoldenleaf.models.Category

class CategoryValidator {

    var error = "Everything is ok."
    fun validate(c: Category): Boolean {
         if(!checkTitle(c.title)){
            error = "Erro. O titulo precisa ter entre 3 e 25 caracteres."
             return false
        }
        return true
    }

    private fun checkTitle(description: String?) = description?.length in 3..25

}