package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        db = DatabaseHandler(this)
        session = SessionManager(this)

        val editLogin = findViewById<EditText>(R.id.editLogin)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonGoRegister = findViewById<Button>(R.id.buttonGoRegister)

        buttonLogin.setOnClickListener {
            val login = editLogin.text.toString().trim()
            val pass = editPassword.text.toString()

            if (login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = db.getUserByLogin(login)
            if (user == null || user.pass != pass) {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            session.saveUser(user.id, user.login, user.isAdmin)
            Log.d("WelcomeActivity", "Вход: ${user.login}, admin=${user.isAdmin}")
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        buttonGoRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}