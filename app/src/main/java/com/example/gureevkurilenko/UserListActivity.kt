package com.example.gureevkurilenko

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class UserListActivity : AppCompatActivity() {
    private val users = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val listView = findViewById<ListView>(R.id.listViewUsers)
        val buttonAdd = findViewById<Button>(R.id.buttonAddUser)

        adapter = ArrayAdapter(this, R.layout.item_user, R.id.textItemUser, users)
        listView.adapter = adapter

        buttonAdd.setOnClickListener {
            Log.d("UserListActivity", "Нажата кнопка Добавить")
            users.add("Пользователь ${users.size + 1}")
            adapter.notifyDataSetChanged()
        }
    }
}