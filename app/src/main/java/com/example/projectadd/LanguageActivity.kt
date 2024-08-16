package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.Locale
import android.content.Context
import android.content.Intent

class LanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val englishTextView = findViewById<TextView>(R.id.eng)
        val hindiTextView = findViewById<TextView>(R.id.hin)

        englishTextView.setOnClickListener {
            setAppLanguage("en")
            val intent= Intent(this,WelcomeActivity::class.java)
            startActivity(intent)

        }

        hindiTextView.setOnClickListener {
            setAppLanguage("hi")
            val intent= Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        saveLanguagePreference(languageCode)
        restartApp()
    }

    private fun saveLanguagePreference(languageCode: String) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("app_language", languageCode)
        editor.apply()
    }

    private fun restartApp() {
        finish()
        startActivity(intent)
    }
}