package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val buttonBack = findViewById<Button>(R.id.buttonBackFromProfile)
        val tvName = findViewById<TextView>(R.id.textViewName)
        val tvSurname = findViewById<TextView>(R.id.textViewSurName)
        val tvPhone = findViewById<TextView>(R.id.textViewPhoneNumber)
        val tvEmail = findViewById<TextView>(R.id.textViewEmail)
        val updatedName = intent.getStringExtra("NEW_NAME")
        val updatedSurname = intent.getStringExtra("NEW_SURNAME")
        val updatedPhone = intent.getStringExtra("NEW_PHONE")
        val updatedEmail = intent.getStringExtra("NEW_EMAIL")

        if (updatedName != null) tvName.text = updatedName
        if (updatedSurname != null) tvSurname.text = updatedSurname
        if (updatedPhone != null) tvPhone.text = updatedPhone
        if (updatedEmail != null) tvEmail.text = updatedEmail

        buttonEdit.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)

            //intent.putExtra("CURRENT_NAME", tvName.text.toString())
            //intent.putExtra("CURRENT_SURNAME", tvSurname.text.toString())
            //intent.putExtra("CURRENT_PHONE", tvPhone.text.toString())
            //intent.putExtra("CURRENT_EMAIL", tvEmail.text.toString())
            startActivity(intent)
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}