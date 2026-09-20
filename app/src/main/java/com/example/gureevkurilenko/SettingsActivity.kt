package com.example.gureevkurilenko

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switch = findViewById<Switch>(R.id.switchTheme)
        val buttonBackFromSettings = findViewById<Button>(R.id.buttonBackFromSettings)

        switch.setOnClickListener {

        }

        buttonBackFromSettings.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }


    }
}