package com.example.projectadd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.models.Medicines

class RecyclerContactAdapter(
    private val context: Context,
    private var dataSet: ArrayList<Medicines>,
    private val incrementQuantity: (Int,String, Int) -> Unit,
    private val decrementQuantity: (Int, String, Int) -> Unit
) : RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = dataSet[position]
        holder.nameTextView.text = contact.name
        holder.quantityTextView.text = contact.quantity.toString()
        holder.incrementIV.setOnClickListener {
            incrementQuantity(position,contact.id, contact.quantity)

        }
        holder.decrementIV.setOnClickListener {
                decrementQuantity(position,contact.id, contact.quantity)

        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: ArrayList<Medicines>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tabb1)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val incrementIV: ImageView = itemView.findViewById(R.id.plusButton)
        val decrementIV: ImageView = itemView.findViewById(R.id.minusButton)
    }
}