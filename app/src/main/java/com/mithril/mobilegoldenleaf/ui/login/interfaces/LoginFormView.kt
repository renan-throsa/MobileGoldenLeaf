package com.mithril.mobilegoldenleaf.ui.login.interfaces

import com.mithril.mobilegoldenleaf.models.Clerk

interface LoginFormView {
    fun login(clerk: Clerk)
    fun showLoginError()
    fun showLoginError(message: String)
}