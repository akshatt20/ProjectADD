package com.example.projectadd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.R
import com.example.projectadd.models.P

class PrescriptionAdapter(private val prescriptions: List<P>) :
    RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    class PrescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val disTextView: TextView = itemView.findViewById(R.id.disTextView)
        val doctorNameTextView: TextView = itemView.findViewById(R.id.doctorNameTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prescription, parent, false)
        return PrescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val prescription = prescriptions[position]
        holder.disTextView.text = prescription.disease
        holder.doctorNameTextView.text = prescription.doctorName
        holder.dateTextView.text = prescription.date.toString() // Format date as needed
    }

    override fun getItemCount(): Int {
        return prescriptions.size
    }
}
