package com.mithril.mobilegoldenleaf.ui.customer

import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerRepository
import com.mithril.mobilegoldenleaf.retrofit.webclient.CustomerWebClient

class CustomerPresenter(private val customerRepository: CustomerRepository) {

    private val customerWebClient = CustomerWebClient()
    private val customerValidator = CustomerValidator()


    fun get(whenSucceeded: (List<Customer>) -> Unit, whenFailed: (error: String?) -> Unit) {
        searchInternally(whenSucceeded)
        searchRemotely(whenSucceeded, whenFailed)
    }

    fun get(id: Long, whenSucceeded: (customerRetrieved: Customer?) -> Unit) {
        BaseAsyncTask(whenExecute = {
            customerRepository.get(id)
        }, whenFinalize = whenSucceeded).execute()
    }

    fun save(customer: Customer,
             whenSucceeded: (customer: Customer) -> Unit,
             whenFailed: (error: String?) -> Unit) {
        if (customerValidator.validate(customer)) {
            saveRemotely(customer, whenSucceeded, whenFailed)
        } else {
            whenFailed(customerValidator.error)
        }

    }


    fun update(customer: Customer,
               whenSucceeded: (newCustomer: Customer) -> Unit,
               whenFailed: (error: String?) -> Unit) {
        if (this.customerValidator.validate(customer)) {
            updateRemotely(customer, whenSucceeded, whenFailed)
        } else {
            whenFailed(customerValidator.error)
        }
    }

    private fun updateRemotely(customer: Customer,
                               whenSucceeded: (updatedCustomer: Customer) -> Unit,
                               whenFailed: (error: String?) -> Unit) {

        customerWebClient.put(customer.id,
                customer,
                whenSucceeded = { updatedCustomer ->
                    updatedCustomer?.let {
                        saveInternally(updatedCustomer, whenSucceeded)
                    }
                },
                whenFailed = whenFailed)
    }

    private fun searchInternally(whenSucceeded: (List<Customer>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            customerRepository.get()
        }, whenFinalize = whenSucceeded)
                .execute()
    }


    private fun searchRemotely(
            whenSucceeded: (List<Customer>) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        customerWebClient.get(
                whenSucceeded = { newCustomers ->
                    newCustomers?.let {
                        saveInternally(newCustomers, whenSucceeded)
                    }
                }, whenFailed = whenFailed
        )
    }


    private fun saveInternally(
            products: List<Customer>,
            whenSucceeded: (newCustomers: List<Customer>) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerRepository.save(products)
                    customerRepository.get()
                }, whenFinalize = whenSucceeded
        ).execute()
    }

    private fun saveInternally(
            customer: Customer,
            whenSucceeded: (newCustomer: Customer) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerRepository.save(customer)
                    customerRepository.get(customer.id)
                }, whenFinalize = { categoryFound -> whenSucceeded(categoryFound) }
        ).execute()
    }


    private fun saveRemotely(
            customer: Customer,
            whenSucceeded: (newCustomer: Customer) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        customerWebClient.post(customer,
                whenSucceeded = { customerSaved ->
                    customerSaved?.let {
                        customerSaved.synchronized = true
                        saveInternally(customerSaved, whenSucceeded)
                    }
                }, whenFailed = whenFailed)
    }
}