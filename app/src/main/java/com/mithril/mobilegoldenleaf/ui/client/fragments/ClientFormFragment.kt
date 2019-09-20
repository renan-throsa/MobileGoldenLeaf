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
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientFormView
import com.mithril.mobilegoldenleaf.ui.client.presenters.ClientFormDialogPresenter

class ClientFormFragment : DialogFragment(), ClientFormView {

    private val presenter by lazy {
        context.let {
            if (it != null) {
                val repository = MobileGoldenLeafDataBase.getInstance(it)
                ClientFormDialogPresenter(this, repository)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialogfragment_product_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clientId = arguments?.getLong(EXTRA_CLIENT_ID)!!
        presenter.loadBy(clientId)
        //form_product_value.setOnEditorActionListener { _, i, _ -> handleKeyBoardEvent(i) }
        dialog.setTitle(R.string.add_product)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    override fun showClient(client: Client) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        private const val DIALOG_TAG = "categoryId"
        private const val EXTRA_CLIENT_ID = "formDialog"

        fun newInstance(id: Long = 0): ClientFormFragment {
            val fragment = ClientFormFragment()
            val args = Bundle()
            args.putLong(EXTRA_CLIENT_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}