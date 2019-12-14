package com.mithril.mobilegoldenleaf.ui.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mithril.mobilegoldenleaf.asynctask.BaseAsyncTask
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.repository.CustomerDao
import com.mithril.mobilegoldenleaf.persistence.repository.Resource
import com.mithril.mobilegoldenleaf.retrofit.webclient.CustomerWebClient

class CustomerRepository(private val customerDao: CustomerDao) {

    private val customerWebClient = CustomerWebClient()
    private val customerValidator = CustomerValidator()
    private val mediator = MediatorLiveData<Resource<List<Customer>?>>()


    fun get(): LiveData<Resource<List<Customer>?>> {

        mediator.addSource(searchInternally(), Observer {
            mediator.value = Resource(data = it)
        })

        val failsOfWebApi = MutableLiveData<Resource<List<Customer>?>>()
        mediator.addSource(failsOfWebApi, Observer {
            val currentResource = mediator.value
            val newResource: Resource<List<Customer>?> = if (currentResource != null) {
                Resource(data = currentResource.data, error = it.error)
            } else {
                it
            }
            mediator.value = newResource
        })
        searchRemotely(whenFailed = { error ->
            failsOfWebApi.value = Resource(data = null, error = error)
        })

        return mediator
    }


    fun get(id: Long): LiveData<Customer> {
        return customerDao.get(id)
    }

    fun save(customer: Customer): LiveData<Resource<Void?>> {
        val liveData = MutableLiveData<Resource<Void?>>()
        saveRemotely(customer, whenSucceeded = {
            liveData.value = Resource(null)
        }, whenFailed = {
            Resource(data = null, error = it)
        })

        return liveData
    }


    fun update(customer: Customer): LiveData<Resource<Void?>> {
        val liveData = MutableLiveData<Resource<Void?>>()
        updateRemotely(customer, whenSucceeded = {
            liveData.value = Resource(null)
        }, whenFailed = {
            Resource(data = null, error = it)
        })
        return liveData
    }

    private fun updateRemotely(customer: Customer,
                               whenSucceeded: () -> Unit,
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

    private fun searchInternally(): LiveData<List<Customer>> {
        return customerDao.get()
    }


    private fun searchRemotely(
            whenFailed: (error: String?) -> Unit
    ) {
        customerWebClient.get(
                whenSucceeded = { newCustomers ->
                    newCustomers?.let {
                        saveInternally(newCustomers)
                    }
                }, whenFailed = whenFailed
        )
    }


    private fun saveInternally(
            products: List<Customer>
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerDao.save(products)
                }, whenFinalize = {}
        ).execute()
    }

    private fun saveInternally(
            customer: Customer,
            whenSucceeded: () -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerDao.insert(customer)
                }, whenFinalize = { whenSucceeded() }
        ).execute()
    }

    private fun updateInternally(
            customer: Customer,
            whenSucceeded: () -> Unit
    ) {
        BaseAsyncTask(
                whenExecute = {
                    customerDao.update(customer)
                }, whenFinalize = { whenSucceeded() }
        ).execute()
    }


    private fun saveRemotely(
            customer: Customer,
            whenSucceeded: () -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        customerWebClient.post(customer,
                whenSucceeded = {
                    it?.let { customerSaved ->
                        customerSaved.synchronized = true
                        saveInternally(customerSaved, whenSucceeded)
                    }
                }, whenFailed = whenFailed)
    }
}