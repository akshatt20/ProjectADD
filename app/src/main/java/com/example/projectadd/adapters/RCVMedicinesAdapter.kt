package com.example.projectadd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.R
import com.example.projectadd.models.M
import com.example.projectadd.models.Md

class RCVMedicinesAdapter(
    private val context: Context,
    private var medicinesList: ArrayList<Md>
) : RecyclerView.Adapter<RCVMedicinesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.medicines_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMedicineName.text = medicinesList[position].name
        holder.tvMedicineQty.text = medicinesList[position].q.toString()

        // Add alternating row colors
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        }
    }

    override fun getItemCount(): Int = medicinesList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMedicineName: TextView = itemView.findViewById(R.id.tvMedicineName)
        val tvMedicineQty: TextView = itemView.findViewById(R.id.tvMedicineQuantity)
    }
}