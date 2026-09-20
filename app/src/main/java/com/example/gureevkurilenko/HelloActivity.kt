package com.example.gureevkurilenko

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class HelloActivity : AppCompatActivity() {

    private val TAG = "HelloActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        Log.d(TAG, "Call: onCreate")

        val buttonSignIn = findViewById<Button>(R.id.buttonSignIn)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        val buttonExit = findViewById<Button>(R.id.buttonExit)
        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        if (savedInstanceState != null) {
            val savedLogin = savedInstanceState.getString("my_login")
            val savedPassword = savedInstanceState.getString("my_password")

            editTextLogin.setText(savedLogin) // Восстанавливаем логин
            editTextPassword.setText(savedPassword) // Восстанавливаем пароль
        }

        buttonSignIn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        buttonSignUp.setOnClickListener {

        }

        buttonExit.setOnClickListener {
            finishAffinity()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Call: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Call: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Call: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Call: onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Call: onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Call: onDestroy")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        Log.d(TAG, "Call: onSaveInstanceState")

        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        outState.putString("my_login", editTextLogin.text.toString())
        outState.putString("my_password", editTextPassword.text.toString())
    }
}