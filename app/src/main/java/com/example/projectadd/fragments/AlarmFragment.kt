package com.example.projectadd.fragments

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projectadd.AlarmReceiver
import com.example.projectadd.R
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AlarmFragment : Fragment() {

    private lateinit var medicineNameEditText: TextInputEditText
    private lateinit var alarmTimeEditText: TextInputEditText
    private lateinit var setAlarmButton: Button
    private lateinit var goToHistoryButton: Button
    private lateinit var alarmLayout: LinearLayout
    private var alarmManager: AlarmManager? = null

    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)

        // Initialize views
        medicineNameEditText = view.findViewById(R.id.medicine_name)
        alarmTimeEditText = view.findViewById(R.id.alarm_time)
        setAlarmButton = view.findViewById(R.id.set_alarm_button)
        alarmLayout = view.findViewById(R.id.alarm_layout)

        // Set click listener for alarm time input
        alarmTimeEditText.setOnClickListener {
            showTimePickerDialog()
        }

        // Set click listener for set alarm button
        setAlarmButton.setOnClickListener {
            val medicineName = medicineNameEditText.text.toString()
            val alarmTime = alarmTimeEditText.text.toString()
            if (medicineName.isNotEmpty() && alarmTime.isNotEmpty()) {
                setAlarm(medicineName, alarmTime)
            } else {
                Toast.makeText(requireContext(), "Please enter medicine name and time", Toast.LENGTH_SHORT).show()
            }
        }

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_REQUEST_CODE)
            }
        }

        return view
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d %s",
                    if (selectedHour > 12) selectedHour - 12 else selectedHour,
                    selectedMinute,
                    if (selectedHour >= 12) "PM" else "AM")
                alarmTimeEditText.setText(formattedTime)
            }, hour, minute, false)

        timePickerDialog.show()
    }

    private fun setAlarm(medicineName: String, alarmTime: String) {
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra("medicine_name", medicineName)
        }

        val requestCode = generateRequestCode(medicineName, alarmTime)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val timeParts = alarmTime.split(" ", ":")
        val hour = if (timeParts[2] == "PM" && timeParts[0].toInt() != 12) timeParts[0].toInt() + 12 else if (timeParts[2] == "AM" && timeParts[0].toInt() == 12) 0 else timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager?.canScheduleExactAlarms() == true) {
                    alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                    Toast.makeText(requireContext(), "Alarm set for $alarmTime", Toast.LENGTH_SHORT).show()
                    addAlarmToLayout(medicineName, alarmTime)
                } else {
                    Toast.makeText(requireContext(), "Cannot set exact alarms, permission not granted.", Toast.LENGTH_SHORT).show()
                }
            } else {
                alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                Toast.makeText(requireContext(), "Alarm set for $alarmTime", Toast.LENGTH_SHORT).show()
                addAlarmToLayout(medicineName, alarmTime)
            }
        } catch (e: SecurityException) {
            Toast.makeText(requireContext(), "Permission to set exact alarms not granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelAlarm(medicineName: String, alarmTime: String) {
        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra("medicine_name", medicineName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            generateRequestCode(medicineName, alarmTime),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager?.cancel(pendingIntent)
        Toast.makeText(requireContext(), "Alarm canceled for $medicineName at $alarmTime", Toast.LENGTH_SHORT).show()
    }

    private fun addAlarmToLayout(medicineName: String, alarmTime: String) {
        val alarmItem = layoutInflater.inflate(R.layout.alarm_item, alarmLayout, false) as LinearLayout
        val timeTextView = alarmItem.findViewById<TextView>(R.id.alarm_time_text)
        val nameTextView = alarmItem.findViewById<TextView>(R.id.alarm_medicine_name)
        val cancelSwitch = alarmItem.findViewById<Switch>(R.id.cancel_alarm_switch)

        timeTextView.text = alarmTime
        nameTextView.text = medicineName

        cancelSwitch.isChecked = true // Set the switch to ON initially
        cancelSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                cancelAlarm(medicineName, alarmTime)
                alarmLayout.removeView(alarmItem)
            }
        }

        alarmLayout.addView(alarmItem)
    }

    private fun generateRequestCode(medicineName: String, alarmTime: String): Int {
        return (medicineName + alarmTime).hashCode()
    }
}
