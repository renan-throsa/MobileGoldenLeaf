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
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.client.interfaces.ClientListView
import com.mithril.mobilegoldenleaf.ui.client.presenters.ClientListPresenter
import kotlinx.android.synthetic.main.fragment_clients_list.view.*

class ClientListFragment : Fragment(), ClientListView {

    private val adapter by lazy {
        context.let {
            if (it != null) {
                ClientAdapter(it)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }

    }

    private val presenter by lazy {
        context.let {
            if (it != null) {
                val repository = MobileGoldenLeafDataBase.getInstance(it).clientRepository
                ClientListPresenter(this, repository)
            } else {
                throw IllegalArgumentException("Contexto inválido")
            }
        }

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


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.category_list_menu, menu)
    }

    override fun onResume() {
        super.onResume()
        presenter.searchClient("")
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        val client = adapter.getItem(menuInfo.position)
        when (item.itemId) {

        }
        return super.onContextItemSelected(item)

    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showClients(all: List<Client>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        txtFooter.text = resources.getQuantityString(R.plurals.footer_text_category, adapter.count, adapter.count)
        txtFooter.setBackgroundColor(Color.LTGRAY)
        txtFooter.gravity = Gravity.END
        txtFooter.setPadding(0, 8, 8, 8)
        return txtFooter
    }

    private fun configFba(view: View) {
        view.fragment_clients_list_fab_new_client.setOnClickListener {
            val dialogFragment = ClientFormFragment.newInstance()
            activity?.supportFragmentManager?.let { it -> dialogFragment.open(it) }
        }
    }
}