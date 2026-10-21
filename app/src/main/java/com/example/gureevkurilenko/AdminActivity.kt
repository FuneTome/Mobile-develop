package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: UserAdapter

     private val uiHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                val usersList = msg.obj as List<User>
                adapter.setData(usersList)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        db = DatabaseHandler(this)
        recycler = findViewById(R.id.recyclerUsers)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter(emptyList()) { user ->
            val i = Intent(this, ProfileActivity::class.java)
            i.putExtra("user_id", user.id)
            startActivity(i)
        }
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadDataAsync()
    }

    private fun loadDataAsync() {
        Thread {
            val users = db.getAllUsers()
            val message = Message.obtain().apply {
                what = 1
                obj = users
            }
            uiHandler.sendMessage(message) // отправляем список в Handler для отображения
        }.start()
    }
}