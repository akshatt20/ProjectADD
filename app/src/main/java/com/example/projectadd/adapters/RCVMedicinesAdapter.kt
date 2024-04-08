package com.example.projectadd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.R
import com.example.projectadd.models.Medicines

class RCVMedicinesAdapter(
    private val context: Context,
    private var medicinesList:ArrayList<Medicines>
) : RecyclerView.Adapter<RCVMedicinesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.medicines_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMedicineName.text = medicinesList[position].name
        holder.tvMedicineQty.text = medicinesList[position].quantity.toString()
    }

    override fun getItemCount(): Int {
        return medicinesList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMedicineName: TextView = itemView.findViewById(R.id.tvMedicineName)
        val tvMedicineQty: TextView = itemView.findViewById(R.id.tvMedicineQuantity)
    }
}