package com.mithril.mobilegoldenleaf.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ClientAdapter
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_clients_list.view.*

class CustomerListFragment : Fragment() {

    private lateinit var activityContext: MainActivity

    private val adapter by lazy {
        ClientAdapter(activityContext)
    }

    private val viewModel by lazy {
        val repository = CustomerRepository(AppDataBase.getInstance(activityContext).customerDao)
        val factory = CustomerViewModelFactory(repository)
        ViewModelProviders.of(this, factory).get(CustomerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_clients_list, null)
        configureList(view)
        configFba(view)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchCustomer()
    }

    private fun searchCustomer() {
        viewModel.getAll().observe(this, Observer { resource ->
            resource.data?.let {
                adapter.update(it)
            }
            resource.error?.let {
                val toast = Toast.makeText(context, R.string.getting_customer_error, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        searchCustomer()
    }

    private fun configureList(view: View) {
        with(view) {
            clients_list.adapter = adapter
        }
    }

//    private fun configFba(view: View) {
//        view.fragment_clients_list_fab_new_client.setOnClickListener {
//            CustomerFormDialog(activityContext, activityContext.window.decorView as ViewGroup)
//                    .showCustomer(
//                            whenSucceeded = { customerCreated: Customer ->
//                                onDataBaseChanged(customerCreated)
//                            },
//                            whenFailed = { errorMessage ->
//                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
//                                        .show()
//                            }
//                    )
//
//        }
//    }

    private fun configFba(view: View) {
        view.fragment_clients_list_fab_new_client.setOnClickListener {
            val dialogFragment = CustomerFormDialog.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val customer = adapter.getItem(item.groupId)
        when (item.itemId) {
            R.id.client_list_menu_edit -> openEditCustomerDialog(customer)
        }
        return super.onContextItemSelected(item)

    }

    private fun openEditCustomerDialog(customer: Customer) {
//        CustomerFormDialog(activityContext, activityContext.window.decorView as ViewGroup, customer.id)
//                .showCustomer(
//                        whenSucceeded = { customerEdited: Customer ->
//                            onDataBaseChanged(customerEdited)
//                        },
//                        whenFailed = { errorMessage ->
//                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
//                                    .show()
//                        }
//                )
        val dialogFragment = CustomerFormDialog.newInstance(customer.id)
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
    }

    private fun onDataBaseChanged(customer: Customer) {
        searchCustomer()
        val text = "Banco de dados atualizado com " + customer.name
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }


    companion object {
        fun newInstance(): CustomerListFragment {
            return CustomerListFragment()

        }
    }
}