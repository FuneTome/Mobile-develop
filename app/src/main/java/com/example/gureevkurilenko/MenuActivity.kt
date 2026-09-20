package com.example.gureevkurilenko

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        val buttonProfile = findViewById<Button>(R.id.buttonProfile)
        val buttonSettings = findViewById<Button>(R.id.buttonSettings)
        val buttonReport = findViewById<Button>(R.id.buttonReport)
        val buttonExitFromMenu = findViewById<Button>(R.id.buttonExitFromMenu)

        buttonStart.setOnClickListener {

        }

        buttonProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        buttonReport.setOnClickListener {
            val phoneNumber = "112"

            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }

            startActivity(intent)
        }

        buttonExitFromMenu.setOnClickListener {
            finishAffinity()
        }
    }
}