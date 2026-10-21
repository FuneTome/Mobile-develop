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

        val textFullName = findViewById<TextView>(R.id.textFullName)
        val textLogin = findViewById<TextView>(R.id.textLogin)
        val textBirthDate = findViewById<TextView>(R.id.textBirthDate)
        val textGender = findViewById<TextView>(R.id.textGender)
        val textIsAdmin = findViewById<TextView>(R.id.textIsAdmin)
        val imageAvatar = findViewById<ImageView>(R.id.imageAvatar)
        val buttonMakeAdmin = findViewById<Button>(R.id.buttonMakeAdmin)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        Thread {
            val user = db.getUserById(userId)

            textFullName.post {
                if (user == null) {
                    finish()
                    return@post
                }
                textFullName.text = user.fullName
                textLogin.text = "Логин: ${user.login}"
                textBirthDate.text = "Дата рождения: ${user.birthDate}"
                textGender.text = "Пол: ${user.gender}"
                textIsAdmin.text = if (user.isAdmin) "Администратор" else "Обычный пользователь"

                if (user.avatarUri.isNotEmpty()) {
                    try {
                        imageAvatar.setImageURI(Uri.parse(user.avatarUri))
                    } catch (_: Exception) {}
                }

                val currentIsAdmin = session.isAdmin()
                val isSelf = session.getUserId() == user.id
                if (currentIsAdmin && !isSelf && !user.isAdmin) {
                    buttonMakeAdmin.visibility = View.VISIBLE
                }

                buttonMakeAdmin.setOnClickListener {
                    buttonMakeAdmin.isEnabled = false
                    Thread {
                        db.setAdmin(user.id, true)
                        buttonMakeAdmin.post {
                            Toast.makeText(this@ProfileActivity, "${user.fullName} теперь администратор", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }.start()
                }
            }
        }.start()

        buttonBack.setOnClickListener { finish() }
    }
}