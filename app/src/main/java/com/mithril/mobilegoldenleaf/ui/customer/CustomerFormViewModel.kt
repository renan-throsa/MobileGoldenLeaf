package com.mithril.mobilegoldenleaf.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.Resource

class CustomerFormViewModel(private val repository: CustomerRepository) : ViewModel() {

    fun save(customer: Customer): LiveData<Resource<Void?>> {
        return if (customer.id != 0L) {
            repository.update(customer)
        } else {
             repository.save(customer)
        }

    }

    fun get(id: Long): LiveData<Customer> {
        return repository.get(id)
    }

}