package com.mithril.mobilegoldenleaf.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerDao
import com.mithril.mobilegoldenleaf.persistence.repository.Resource
import com.mithril.mobilegoldenleaf.retrofit.webclient.CustomerWebClient

class CustomerRepository(private val customerDao: CustomerDao) {

    private val customerWebClient = CustomerWebClient()
    private val customerValidator = CustomerValidator()
    private val liveData = MutableLiveData<Resource<List<Customer>?>>()

    fun get(): LiveData<Resource<List<Customer>?>> {
        searchInternally(whenSucceeded = {
            liveData.value = Resource(data = it)
        })
        searchRemotely(whenSucceeded = {
            liveData.value = Resource(data = it)
        }, whenFailed = {
            val currentResource = liveData.value
            val createdResource: Resource<List<Customer>?> = if (currentResource != null) {
                Resource(data = currentResource.data, error = it)
            } else {
                Resource(data = null, error = it)
            }
            liveData.value = createdResource
        })

        return liveData
    }

    fun get(id: Long, whenSucceeded: (customerRetrieved: Customer?) -> Unit) {
        BaseAsyncTask(whenExecute = {
            customerDao.get(id)
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
                        updateInternally(updatedCustomer, whenSucceeded)
                    }
                },
                whenFailed = whenFailed)
    }

    private fun searchInternally(whenSucceeded: (List<Customer>) -> Unit) {
        BaseAsyncTask(whenExecute = {
            customerDao.get()
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
                    customerDao.save(products)
                    customerDao.get()
                }, whenFinalize = whenSucceeded
        ).execute()
    }

    private fun saveInternally(
            customer: Customer,
            whenSucceeded: (newCustomer: Customer) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerDao.save(customer)
                    customerDao.get(customer.id)
                }, whenFinalize = { customerFound -> whenSucceeded(customerFound) }
        ).execute()
    }

    private fun updateInternally(
            customer: Customer,
            whenSucceeded: (newCustomer: Customer) -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerDao.update(customer)
                    customerDao.get(customer.id)
                }, whenFinalize = { customerFound -> whenSucceeded(customerFound) }
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