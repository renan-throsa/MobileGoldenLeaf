package com.mithril.mobilegoldenleaf.ui.client.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientFormView
import com.mithril.mobilegoldenleaf.ui.client.presenters.ClientFormDialogPresenter
import kotlinx.android.synthetic.main.dialogfragment_client_form.*
import kotlinx.android.synthetic.main.dialogfragment_client_form.view.*

class ClientFormDialogFragment : DialogFragment(), ClientFormView {
    private lateinit var activityContext: MainActivity

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(activityContext)
        ClientFormDialogPresenter(this, repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.CustomDialogFragment)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialogfragment_client_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val clientId = arguments?.getLong(EXTRA_CLIENT_ID) ?: 0L
        if (clientId != 0L) {
            presenter.loadBy(clientId)
            dialog.setTitle(R.string.edit_client)
        }
        dialog.setTitle(R.string.add_client)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        view.form_client_address_street.setOnEditorActionListener { _, i, _ -> handleKeyBoardEvent(i) }

    }

    private fun handleKeyBoardEvent(i: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == i) {
            val address = saveAddress()
            if (address != null) {
                val client = saveClient(address.id)
                if (client != null) {
                    showSuccessMessage(client.name)
                } else {
                    presenter.delete(address)
                }
            }
            dialog.dismiss()
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_action_concluded_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.fragment_form_action_concluded) {
            val address = saveAddress()
            if (address != null && address.id != 0L) {
                val client = saveClient(address.id)
                if (client != null) {
                    showSuccessMessage(client.name)
                } else {
                    presenter.delete(address)
                }
            }
            dialog.dismiss()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSuccessMessage(name: String?) {
        val text = name + " salvo com sucesso!"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(activityContext, text, duration)
        toast.show()

    }

    private fun saveClient(addressId: Long): Client? {
        val client = fillClientOut(addressId)
        return if (presenter.save(client)) {
            client
        } else {
            null
        }
    }

    private fun fillClientOut(addressId: Long): Client {
        val client = Client()
        val clientId = arguments?.getLong(EXTRA_CLIENT_ID) ?: 0L
        if (clientId != 0L) {
            client.id = clientId
        }
        client.name = form_client_name.text.toString()
        client.addressId = addressId
        client.identification = form_client_identification.text.toString()
        client.phoneNumber = form_client_phoneNumber.text.toString()
        return client
    }

    private fun saveAddress(): Address? {
        val address = fillAddressOut()
        return if (presenter.save(address)) {
            address
        } else {
            null
        }
    }

    private fun fillAddressOut(): Address {
        val address = Address()
        address.street = form_client_address_street.text.toString()
        address.zipCode = form_client_address_zipCode.text.toString()
        return address
    }

    override fun showClient(client: Client, address: Address) {
        form_client_name.setText(client.name)
        form_client_identification.setText(client.identification)
        form_client_phoneNumber.setText(client.phoneNumber)
        form_client_address_zipCode.setText(address.zipCode)
        form_client_address_street.setText(address.street)
    }

    override fun clientInvalidError() {
        val toast = Toast.makeText(context, R.string.client_invalid_error, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun savingClientError() {
        val toast = Toast.makeText(context, R.string.client_saving_error, Toast.LENGTH_SHORT)
        toast.show()

    }

    override fun savingAddressError() {
        val toast = Toast.makeText(context, R.string.address_saving_error, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun addressInvalidError() {
        val toast = Toast.makeText(context, R.string.address_invalid_error, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null)
            show(fm, DIALOG_TAG)
    }


    companion object {
        private const val DIALOG_TAG = "ClientFormDialogFragment"
        private const val EXTRA_CLIENT_ID = "clientId"

        fun newInstance(id: Long = 0): ClientFormDialogFragment {
            val fragment = ClientFormDialogFragment()
            val args = Bundle()
            args.putLong(EXTRA_CLIENT_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}