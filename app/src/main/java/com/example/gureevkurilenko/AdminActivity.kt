package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val db = DatabaseHandler(this)
        val recycler = findViewById<RecyclerView>(R.id.recyclerUsers)

        val adapter = UserAdapter(db.getAllUsers()) { user ->
            val i = Intent(this, ProfileActivity::class.java)
            i.putExtra("user_id", user.id)
            startActivity(i)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val recycler = findViewById<RecyclerView>(R.id.recyclerUsers)
        (recycler.adapter as? UserAdapter)?.setData(DatabaseHandler(this).getAllUsers())
    }
}