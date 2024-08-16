package com.example.projectadd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.models.M
import com.example.projectadd.models.Md
import java.util.Locale

class RecyclerContactAdapter(
    private val context: Context,
    private var dataSet: ArrayList<M>,
    private val incrementQuantity: (Int,Int, Int) -> Unit,
    private val decrementQuantity: (Int, Int, Int) -> Unit
) : RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder>() {
    private var filteredData: ArrayList<M> = dataSet
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<M>()
                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(dataSet)
                } else {
                    val filterPattern = constraint.toString().trim().toLowerCase(Locale.ROOT)
                    for (item in dataSet) {
                        if (item.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as? ArrayList<M> ?: ArrayList()
                notifyDataSetChanged()
            }
        }
    }

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

    fun updateData(newData: ArrayList<M>) {
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