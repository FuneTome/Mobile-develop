package com.example.gureevkurilenko

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private var currentUser: User? = null

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null && currentUser != null) {
            val newAvatarUri = saveImageToInternalStorage(bitmap)
            findViewById<ImageView>(R.id.imageAvatar).setImageBitmap(bitmap)

            Thread {
                db.updateUserAvatar(currentUser!!.id, newAvatarUri)
                runOnUiThread {
                    Toast.makeText(this, "Аватар обновлен", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = DatabaseHandler(this)
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
            currentUser = user

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

                if (isSelf) {
                    imageAvatar.setOnClickListener {
                        takePicture.launch(null)
                    }
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

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val file = File(filesDir, "avatar_${System.currentTimeMillis()}.jpg")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        stream.close()
        return Uri.fromFile(file).toString()
    }
}