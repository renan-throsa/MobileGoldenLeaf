package com.mithril.mobilegoldenleaf.ui.customer

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import kotlinx.android.synthetic.main.dialog_client_form.view.*

class CustomerFormDialog(private val context: Context, private val viewGroup: ViewGroup?,
                         private val customerId: Long = 0L) {


    private val view = createView()
    private val NEGATIVE_BUTTON_TITLE = "Cancelar"
    private val POSITIVE_BUTTON_TITLE = "Salvar"
    private val DIALOG_TITLE: String
        get() {
            return if (customerId != 0L) {
                "Editar cliente"
            } else {
                "Novo cliente"
            }

        }

    private val customerPresenter by lazy {
        val repository = AppDataBase.getInstance(context).customerRepository
        CustomerPresenter(repository)
    }


    fun show(whenSucceeded: (customer: Customer) -> Unit, whenFailed: (error: String?) -> Unit) {
        if (customerId != 0L) {
            customerPresenter.get(customerId, whenSucceeded = { customerFound ->
                if (customerFound != null) {
                    show(customerFound)
                }
            })
        }
        AlertDialog.Builder(context)
                .setTitle(DIALOG_TITLE)
                .setView(view)
                .setPositiveButton(POSITIVE_BUTTON_TITLE) { _, _ ->
                    save(Customer(
                            view.form_client_name.text.toString(),
                            view.form_client_phoneNumber.text.toString(),
                            view.form_client_identification.text.toString(),
                            view.form_client_address.text.toString(),
                            true),

                            whenSucceeded, whenFailed)
                }
                .setNegativeButton(NEGATIVE_BUTTON_TITLE, null)
                .show()

    }

    private fun show(customer: Customer) {
        with(view) {
            form_client_name.setText(customer.name)
            form_client_identification.setText(customer.identification)
            form_client_phoneNumber.setText(customer.phone_number)
            form_client_address.setText(customer.address)
        }

    }

    private fun save(customer: Customer,
                     whenSucceeded: (customer: Customer) -> Unit,
                     whenFailed: (error: String?) -> Unit) {


        if (customerId != 0L) {
            customer.id = customerId
            customerPresenter.update(customer, whenSucceeded = whenSucceeded, whenFailed = whenFailed)
        } else {
            customerPresenter.save(customer, whenSucceeded = whenSucceeded, whenFailed = whenFailed)
        }

    }

    private fun createView(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.dialog_client_form, viewGroup, false)

    }
}