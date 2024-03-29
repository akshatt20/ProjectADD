package com.example.projectadd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerContactAdapter(private val context: Context, private val arrContact: ArrayList<ContactModel>) : RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tabb1 = itemView.findViewById<TextView>(R.id.tabb1) // Assuming tabb1 is a TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = arrContact[position]
        holder.tabb1.text = contact.name // Use contact.name directly
    }

    override fun getItemCount(): Int {
        return arrContact.size
    }
}
