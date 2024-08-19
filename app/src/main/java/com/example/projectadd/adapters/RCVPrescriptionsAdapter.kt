package com.example.projectadd.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectadd.DeactivatedPrescriptionDetailsActivity
import com.example.projectadd.R
import com.example.projectadd.models.P
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class RCVPrescriptionsAdapter(
    private val context: Context,
    private var prescriptionsList: ArrayList<P>,
    private var listener: PrescriptionListener
) : RecyclerView.Adapter<RCVPrescriptionsAdapter.ViewHolder>() {

    private var filteredList: ArrayList<P> = ArrayList(prescriptionsList)  // Holds the filtered list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.prescription_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prescription = filteredList[position]

        // Bind the prescription data to the TextViews
        holder.tvDoctorName.text = prescription.doctorName
        holder.tvDiseaseName.text = prescription.disease

        // Format the date and time
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        holder.tvDate.text = dateFormat.format(prescription.date)
        holder.tvTime.text = timeFormat.format(prescription.date)

        // Handle item click
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeactivatedPrescriptionDetailsActivity::class.java)
            val prescriptionJson = Gson().toJson(prescription)
            intent.putExtra("PRESCRIPTION", prescriptionJson)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    // Interface to handle click events
    interface PrescriptionListener {
        fun onPrescriptionClicked(p: P)
    }

    // ViewHolder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDoctorName: TextView = itemView.findViewById(R.id.tvDoctorName)
        val tvDiseaseName: TextView = itemView.findViewById(R.id.tvDiseaseName)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    // Method to update the list
    fun updateList(newPrescriptions: ArrayList<P>) {
        prescriptionsList = newPrescriptions
        filteredList = ArrayList(prescriptionsList)
        notifyDataSetChanged()
    }

    // Method to filter the list based on the query string
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            ArrayList(prescriptionsList)  // If the query is empty, reset to the full list
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

            // Filter the prescriptions list by doctor name, disease, date, and time
            val filtered = prescriptionsList.filter { prescription ->
                prescription.doctorName.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        prescription.disease.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        dateFormat.format(prescription.date).contains(lowerCaseQuery) ||
                        timeFormat.format(prescription.date).contains(lowerCaseQuery)
            }
            ArrayList(filtered)
        }
        notifyDataSetChanged()
    }
}
