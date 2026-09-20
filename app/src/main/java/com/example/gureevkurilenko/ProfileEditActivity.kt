package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ProfileEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etName = findViewById<EditText>(R.id.editTextEditName)
        val etSurname = findViewById<EditText>(R.id.editTextEditSurname)
        val etPhone = findViewById<EditText>(R.id.editTextEditPhone)
        val etEmail = findViewById<EditText>(R.id.editTextEditEmail)

        val buttonSaveProfile = findViewById<Button>(R.id.buttonSaveProfile)
        val buttonBackFromEditProfile = findViewById<Button>(R.id.buttonBackFromEditProfile)

        etName.setText(intent.getStringExtra("CURRENT_NAME"))
        etSurname.setText(intent.getStringExtra("CURRENT_SURNAME"))
        etPhone.setText(intent.getStringExtra("CURRENT_PHONE"))
        etEmail.setText(intent.getStringExtra("CURRENT_EMAIL"))

        buttonSaveProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)

            intent.putExtra("NEW_NAME", etName.text.toString())
            intent.putExtra("NEW_SURNAME", etSurname.text.toString())
            intent.putExtra("NEW_PHONE", etPhone.text.toString())
            intent.putExtra("NEW_EMAIL", etEmail.text.toString())

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        buttonBackFromEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}