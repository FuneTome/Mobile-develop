package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val session = SessionManager(this)
        val db = DatabaseHandler(this)
        val textWelcome = findViewById<TextView>(R.id.textWelcome)
        val buttonAdmin = findViewById<Button>(R.id.buttonAdmin)

        Thread {
            val user = db.getUserById(session.getUserId())

            textWelcome.post {
                if (user == null) {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                } else {
                    textWelcome.text = "Привет, ${user.fullName}"
                    if (user.isAdmin) buttonAdmin.visibility = View.VISIBLE

                    findViewById<Button>(R.id.buttonProfile).setOnClickListener {
                        val i = Intent(this, ProfileActivity::class.java)
                        i.putExtra("user_id", user.id)
                        startActivity(i)
                    }
                }
            }
        }.start()

        buttonAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }
        findViewById<Button>(R.id.buttonUserList).setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
        }
        findViewById<Button>(R.id.buttonSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        findViewById<Button>(R.id.buttonLogout).setOnClickListener {
            session.logout()
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}