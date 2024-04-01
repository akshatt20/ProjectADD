package com.example.projectadd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import java.util.*

class RecyclerContactAdapter(
    private val context: Context,
    private var contactList: List<ContactModel>,
    private val listener: QuantityUpdateListener
) : RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder>(), Filterable {

    private var contactListFiltered: List<ContactModel> = contactList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact: ContactModel = contactListFiltered[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contactListFiltered.size
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.tabb1)
        private val itemQuantity: TextView = itemView.findViewById(R.id.quantityTextView)

        fun bind(contact: ContactModel) {
            itemName.text = contact.name
            itemQuantity.text = contact.quantity.toString()

            itemView.findViewById<View>(R.id.plusButton).setOnClickListener {
                listener.onIncrementClicked(contact.price, contact.quantity)
            }

            itemView.findViewById<View>(R.id.minusButton).setOnClickListener {
                listener.onDecrementClicked(contact.price, contact.quantity)
            }
        }
    }

    fun updateQuantity(itemId: String, newQuantity: Int) {
        contactListFiltered.find { it.price == itemId }?.let {
            it.quantity = newQuantity
            notifyDataSetChanged()
        }
    }

    interface QuantityUpdateListener {
        fun onIncrementClicked(itemId: String, currentQuantity: Int)
        fun onDecrementClicked(itemId: String, currentQuantity: Int)
    }

    // Implement filter logic if needed
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                contactListFiltered = if (charSearch.isEmpty()) {
                    contactList
                } else {
                    val resultList = ArrayList<ContactModel>()
                    for (row in contactList) {
                        if (row.name.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactListFiltered = results?.values as ArrayList<ContactModel>
                notifyDataSetChanged()
            }
        }
    }
}