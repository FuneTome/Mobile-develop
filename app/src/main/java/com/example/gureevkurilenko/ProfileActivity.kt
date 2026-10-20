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

            db.updateUserAvatar(currentUser!!.id, newAvatarUri)
            Toast.makeText(this, "Аватар обновлен", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = DatabaseHandler(this)
        val session = SessionManager(this)

        val userId = intent.getIntExtra("user_id", -1)
        currentUser = db.getUserById(userId)

        if (currentUser == null) {
            finish()
            return
        }

        findViewById<TextView>(R.id.textFullName).text = currentUser!!.fullName
        findViewById<TextView>(R.id.textLogin).text = "Логин: ${currentUser!!.login}"
        findViewById<TextView>(R.id.textBirthDate).text = "Дата рождения: ${currentUser!!.birthDate}"
        findViewById<TextView>(R.id.textGender).text = "Пол: ${currentUser!!.gender}"
        findViewById<TextView>(R.id.textIsAdmin).text =
            if (currentUser!!.isAdmin) "Администратор" else "Обычный пользователь"

        val imageAvatar = findViewById<ImageView>(R.id.imageAvatar)
        if (currentUser!!.avatarUri.isNotEmpty()) {
            try {
                imageAvatar.setImageURI(Uri.parse(currentUser!!.avatarUri))
            } catch (_: Exception) {}
        }

        val isSelf = session.getUserId() == currentUser!!.id
        if (isSelf) {
            imageAvatar.setOnClickListener {
                takePicture.launch(null)
            }
        }

        val buttonMakeAdmin = findViewById<Button>(R.id.buttonMakeAdmin)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        val currentIsAdmin = session.isAdmin()

        if (currentIsAdmin && !isSelf && !currentUser!!.isAdmin) {
            buttonMakeAdmin.visibility = View.VISIBLE
        }

        buttonMakeAdmin.setOnClickListener {
            db.setAdmin(currentUser!!.id, true)
            Toast.makeText(this, "${currentUser!!.fullName} теперь администратор", Toast.LENGTH_SHORT).show()
            finish()
        }

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