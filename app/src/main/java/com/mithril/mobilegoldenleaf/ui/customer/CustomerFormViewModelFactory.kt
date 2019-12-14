package com.mithril.mobilegoldenleaf.ui.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerFormViewModelFactory(private val repository: CustomerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CustomerFormViewModel(repository) as T
    }
}