package com.example.projectadd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerContactAdapter(
    private val context: Context,
    private val arrContact: ArrayList<ContactModel>,
    private val incrementQuantity: (String, Int) -> Unit,
    private val decrementQuantity: (String, Int) -> Unit
) : RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tabb1)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val plusButton: ImageView = itemView.findViewById(R.id.plusButton)
        val minusButton: ImageView = itemView.findViewById(R.id.minusButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = arrContact[position]

        holder.nameTextView.text = contact.name
        holder.quantityTextView.text = contact.quantity.toString()

        // Set click listeners for plus and minus buttons
        holder.plusButton.setOnClickListener {
            // Increment quantity and update UI
            contact.quantity++
            notifyDataSetChanged() // Notify adapter that data has changed
        }

        holder.minusButton.setOnClickListener {
            // Decrement quantity if greater than 0 and update UI
            if (contact.quantity > 0) {
                contact.quantity--
                notifyDataSetChanged() // Notify adapter that data has changed
            }
        }
    }

    override fun getItemCount(): Int {
        return arrContact.size
    }
}

