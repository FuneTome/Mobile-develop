package com.example.gureevkurilenko

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val session = SessionManager(this)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupTheme)

        when (session.getTheme()) {
            "light" -> radioGroup.check(R.id.radioLight)
            "dark" -> radioGroup.check(R.id.radioDark)
            else -> radioGroup.check(R.id.radioSystem)
        }

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
            val mode = when (radioGroup.checkedRadioButtonId) {
                R.id.radioLight -> "light"
                R.id.radioDark -> "dark"
                else -> "system"
            }
            session.setTheme(mode)

            when (mode) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            finish()
        }
    }
}