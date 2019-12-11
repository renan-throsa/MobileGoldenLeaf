package com.mithril.mobilegoldenleaf.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.Resource

class CustomerViewModel(private val repository: CustomerRepository) : ViewModel() {

    fun getAll(): LiveData<Resource<List<Customer>?>> {
        return repository.get()
    }


}