package com.example.gureevkurilenko

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val db = DatabaseHandler(this)
        val session = SessionManager(this)

        val userId = intent.getIntExtra("user_id", -1)
        val user = db.getUserById(userId)
        if (user == null) { finish(); return }

        findViewById<TextView>(R.id.textFullName).text = user.fullName
        findViewById<TextView>(R.id.textLogin).text = "Логин: ${user.login}"
        findViewById<TextView>(R.id.textBirthDate).text = "Дата рождения: ${user.birthDate}"
        findViewById<TextView>(R.id.textGender).text = "Пол: ${user.gender}"
        findViewById<TextView>(R.id.textIsAdmin).text =
            if (user.isAdmin) "Администратор" else "Обычный пользователь"

        if (user.avatarUri.isNotEmpty()) {
            try {
                findViewById<ImageView>(R.id.imageAvatar).setImageURI(Uri.parse(user.avatarUri))
            } catch (_: Exception) {}
        }

        val buttonMakeAdmin = findViewById<Button>(R.id.buttonMakeAdmin)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        val currentIsAdmin = session.isAdmin()
        val isSelf = session.getUserId() == user.id

        if (currentIsAdmin && !isSelf && !user.isAdmin) {
            buttonMakeAdmin.visibility = View.VISIBLE
        }

        buttonMakeAdmin.setOnClickListener {
            db.setAdmin(user.id, true)
            Toast.makeText(this, "${user.fullName} теперь администратор", Toast.LENGTH_SHORT).show()
            finish()
        }

        buttonBack.setOnClickListener { finish() }
    }
}