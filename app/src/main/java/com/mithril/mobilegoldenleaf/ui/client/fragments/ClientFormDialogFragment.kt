package com.mithril.mobilegoldenleaf.ui.client.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientFormView
import com.mithril.mobilegoldenleaf.ui.client.presenters.ClientFormDialogPresenter
import kotlinx.android.synthetic.main.dialogfragment_client_form.*

class ClientFormDialogFragment : DialogFragment(), ClientFormView {
    private lateinit var activityContext: MainActivity

    private val presenter by lazy {
        val repository = MobileGoldenLeafDataBase.getInstance(activityContext)
        ClientFormDialogPresenter(this, repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityContext = activity as MainActivity
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialogfragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clientId = arguments?.getLong(EXTRA_CLIENT_ID) ?: 0L
        if (clientId != 0L) {
            presenter.loadBy(clientId)
            dialog.setTitle(R.string.edit_client)
        }
        dialog.setTitle(R.string.add_client)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    override fun showClient(client: Client) {
        form_client_name.setText(client.name)
        form_client_identification.setText(client.identification)
        form_client_phoneNumber.setText(client.phoneNumber)

    }

    override fun clientInvalidError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savingClientError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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