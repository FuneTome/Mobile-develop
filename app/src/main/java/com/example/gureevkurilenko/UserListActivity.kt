package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val db = DatabaseHandler(this)
        val recycler = findViewById<RecyclerView>(R.id.recyclerUsers)
        val buttonAdd = findViewById<Button>(R.id.buttonAddUser)

        val adapter = UserAdapter(db.getAllUsers()) { user ->
            Log.d("UserListActivity", "Клик по ${user.fullName}")
            val i = Intent(this, ProfileActivity::class.java)
            i.putExtra("user_id", user.id)
            startActivity(i)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        // в самостоятельной работе кнопка добавления не нужна, но пусть будет
        buttonAdd.visibility = Button.GONE
    }
}