package com.mithril.mobilegoldenleaf.ui.client.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.adapters.ClientAdapter
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.ui.MainActivity
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientListView
import com.mithril.mobilegoldenleaf.ui.client.presenters.ClientListPresenter
import kotlinx.android.synthetic.main.fragment_clients_list.view.*

class ClientListFragment : Fragment(), ClientListView {

    private lateinit var activityContext: MainActivity

    private val adapter by lazy {
        ClientAdapter(activityContext)
    }

    private val presenter by lazy {
        val repository = AppDataBase.getInstance(activityContext).clientRepository
        ClientListPresenter(this, repository)
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
        presenter.searchClient("")
    }


    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.client_list_menu, menu)
    }

    override fun onResume() {
        super.onResume()
        presenter.searchClient("")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        val client = adapter.getItem(menuInfo.position)
        when (item.itemId) {
            R.id.client_list_menu_edit -> openEditProductDialogFragment(client)
        }
        return super.onContextItemSelected(item)

    }

    private fun openEditProductDialogFragment(client: Client) {
        val dialogFragment = ClientFormDialogFragment.newInstance(client.id)
        activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showClients(all: List<Client>) {
        adapter.update(all)
    }

    override fun showOrdersOf(client: Client) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun configureList(view: View) {
        with(view) {
            clients_list.adapter = adapter
            registerForContextMenu(clients_list)
            clients_list.addFooterView(initFooter())
        }
    }

    private fun initFooter(): TextView {
        val txtFooter = TextView(context)
        txtFooter.text = resources.getQuantityString(R.plurals.footer_text_client, adapter.count, adapter.count)
        txtFooter.setBackgroundColor(Color.LTGRAY)
        txtFooter.gravity = Gravity.END
        txtFooter.setPadding(0, 8, 8, 8)
        return txtFooter
    }

    private fun configFba(view: View) {
        view.fragment_clients_list_fab_new_client.setOnClickListener {
            val dialogFragment = ClientFormDialogFragment.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }

    companion object {
//        private const val EXTRA_CLIENT_ID = "categoryId"
//        private const val TAG_CLIENT_LIST = "tagProductsList"

        fun newInstance(): ClientListFragment {
            val fragment = ClientListFragment()
            return fragment
        }
    }
}