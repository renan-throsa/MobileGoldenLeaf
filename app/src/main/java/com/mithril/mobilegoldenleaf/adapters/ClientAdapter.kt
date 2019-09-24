package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Client
import kotlinx.android.synthetic.main.item_client_row.view.*

class ClientAdapter(private val context: Context) : BaseAdapter() {

    private val clients: ArrayList<Client> = ArrayList()


    override fun getCount(): Int = clients.size


    override fun getItem(position: Int): Client = clients[position]


    override fun getItemId(i: Int): Long = clients[i].id


    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {
        val product = clients[position]
        val holder: ViewHolder
        val row: View
        if (convertView == null) {
            row = getInflate(viewGroup)
            holder = ViewHolder(row)
            row.tag = holder
        } else {
            row = convertView
            holder = convertView.tag as ViewHolder
        }

        boundInformation(holder, product)
        return row
    }

    private fun getInflate(viewGroup: ViewGroup): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_client_row, viewGroup, false)
    }

    private fun boundInformation(row: ViewHolder, c: Client) {
        row.name.text = c.name
        row.phoneNumber.text = c.phoneNumber
        row.identification.text = c.identification
    }

    fun update(all: List<Client>) {
        clients.clear()
        clients.addAll(all)
        notifyDataSetChanged()
    }

    companion object {
        data class ViewHolder(val view: View) {
            val name: TextView = view.item_client_name
            val phoneNumber: TextView = view.item_client_phoneNumber
            val identification: TextView = view.item_client_identification

        }
    }
}