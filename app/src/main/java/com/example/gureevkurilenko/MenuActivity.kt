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
        val user = db.getUserById(session.getUserId())

        if (user == null) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }

        // необязательно: добавить в activity_menu.xml TextView с id=textWelcome
        findViewById<TextView>(R.id.textWelcome).text = "Привет, ${user.fullName}"

        val buttonAdmin = findViewById<Button>(R.id.buttonAdmin)
        if (user.isAdmin) buttonAdmin.visibility = View.VISIBLE

        findViewById<Button>(R.id.buttonProfile).setOnClickListener {
            val i = Intent(this, ProfileActivity::class.java)
            i.putExtra("user_id", user.id)
            startActivity(i)
        }

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