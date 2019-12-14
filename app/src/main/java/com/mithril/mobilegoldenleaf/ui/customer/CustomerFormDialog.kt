package com.mithril.mobilegoldenleaf.ui.customer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import kotlinx.android.synthetic.main.dialog_client_form.*

class CustomerFormDialog : DialogFragment() {

    private lateinit var activityContext: MainActivity


    private val viewModel by lazy {
        val repository = CustomerRepository(AppDataBase.getInstance(activityContext).customerDao)
        val factory = CustomerFormViewModelFactory(repository)
        ViewModelProviders.of(this, factory)
                .get(CustomerFormViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.CustomDialogFragment)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_client_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val customerId = arguments?.getLong(EXTRA_CUSTOMER_ID) ?: 0L
        if (customerId != 0L) {
            val customerFound = viewModel.get(customerId).value
            dialog?.setTitle(R.string.edit_client)
            customerFound?.let { showCustomer(it) }
        }

        dialog?.setTitle(R.string.add_client)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    private fun showCustomer(customer: Customer) {
        form_client_name.setText(customer.name)
        form_client_identification.setText(customer.identification)
        form_client_phoneNumber.setText(customer.phone_number)
        form_client_address.setText(customer.address)

    }

    private fun save(customer: Customer) {

        viewModel.save(customer).observe(this, Observer {
            if (it.error == null) {
                this.dismiss()
            } else {

            }
        })


    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
    }

    companion object {
        private const val DIALOG_TAG = "ClientFormDialogFragment"
        private const val EXTRA_CUSTOMER_ID = "customerId"

        fun newInstance(id: Long = 0): CustomerFormDialog {
            val fragment = CustomerFormDialog()
            val args = Bundle()
            args.putLong(EXTRA_CUSTOMER_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}