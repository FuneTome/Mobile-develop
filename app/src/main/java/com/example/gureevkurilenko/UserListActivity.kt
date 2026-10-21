package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserListActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        db = DatabaseHandler(this)
        recycler = findViewById(R.id.recyclerUsers)
        val buttonAdd = findViewById<Button>(R.id.buttonAddUser)

        adapter = UserAdapter(emptyList()) { user ->
            val i = Intent(this, ProfileActivity::class.java)
            i.putExtra("user_id", user.id)
            startActivity(i)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        buttonAdd.visibility = Button.GONE
    }

    override fun onResume() {
        super.onResume()
        Thread {
            val users = db.getAllUsers()
            recycler.post {
                adapter.setData(users)
            }
        }.start()
    }
}