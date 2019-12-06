package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Customer
import kotlinx.android.synthetic.main.item_client_row.view.*

class ClientAdapter(private val context: Context) : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {

    private val customers: ArrayList<Customer> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client = customers[position]
        holder.bindView(client)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_client_row, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = customers.size

    override fun getItemId(i: Int): Long = customers[i].id

    fun getItem(position: Int): Customer = customers[position]


    fun update(all: List<Customer>) {
        notifyItemRangeRemoved(0, this.customers.size)
        customers.clear()
        customers.addAll(all)
        notifyItemRangeInserted(0, this.customers.size)
    }

    companion object {
        data class ViewHolder(val view: View) {
            val name: TextView = view.item_client_name
            val phoneNumber: TextView = view.item_client_phoneNumber
            val identification: TextView = view.item_client_identification

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, R.id.client_list_menu_edit, Menu.NONE, R.string.edit_client)
            menu?.add(adapterPosition, R.id.client_list_menu_add_order, Menu.NONE, R.string.add_order)
            menu?.add(adapterPosition, R.id.client_list_menu_see_orders, Menu.NONE, R.string.see_orders)
        }

        init {
            itemView.setOnCreateContextMenuListener(this)
        }


        fun bindView(customer: Customer) {
            itemView.item_client_name.text = customer.name
            itemView.item_client_phoneNumber.text = customer.phoneNumber
            itemView.item_client_identification.text = customer.identification

        }


    }
}