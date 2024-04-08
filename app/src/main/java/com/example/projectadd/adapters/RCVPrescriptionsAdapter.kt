package com.example.projectadd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.R
import com.example.projectadd.models.Medicines
import com.example.projectadd.models.Prescription

class RCVPrescriptionsAdapter(
    private val context: Context,
    private var prescriptionsList:ArrayList<Prescription>,
    private var listener:PrescriptionListener
) : RecyclerView.Adapter<RCVPrescriptionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.prescription_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMedicineName.text = prescriptionsList[position].disease
        holder.tvMedicineName.setOnClickListener {
            listener.onPrescriptionClicked(prescriptionsList[position])

        }
    }

    override fun getItemCount(): Int {
        return prescriptionsList.size
    }

    interface PrescriptionListener{
        fun onPrescriptionClicked(prescription: Prescription)
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMedicineName: TextView = itemView.findViewById(R.id.tvPrescriptionName)

    }
}