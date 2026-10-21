package com.example.gureevkurilenko

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class RegistrationActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private var avatarUri: String = ""

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            avatarUri = saveImageToInternalStorage(bitmap)
            findViewById<ImageView>(R.id.imageAvatar).setImageBitmap(bitmap)
            Log.d("RegistrationActivity", "Аватар сохранен: $avatarUri")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        db = DatabaseHandler(this)

        val editLogin = findViewById<EditText>(R.id.editLogin)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editFullName = findViewById<EditText>(R.id.editFullName)
        val editBirthDate = findViewById<EditText>(R.id.editBirthDate)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupGender)
        val imageAvatar = findViewById<ImageView>(R.id.imageAvatar)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        editBirthDate.setOnClickListener { showDatePicker(editBirthDate) }
        imageAvatar.setOnClickListener { takePicture.launch(null) }

        buttonRegister.setOnClickListener {
            val login = editLogin.text.toString().trim()
            val pass = editPassword.text.toString()
            val fullName = editFullName.text.toString().trim()
            val birthDate = editBirthDate.text.toString()
            val gender = when (radioGroup.checkedRadioButtonId) {
                R.id.radioMale -> "М"
                R.id.radioFemale -> "Ж"
                else -> ""
            }

            if (login.isEmpty() || pass.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(this, "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            buttonRegister.isEnabled = false

            Thread {
                if (db.getUserByLogin(login) != null) {
                    buttonRegister.post {
                        buttonRegister.isEnabled = true
                        Toast.makeText(this, "Логин уже занят", Toast.LENGTH_SHORT).show()
                    }
                    return@Thread
                }

                val isFirstUser = db.getUserCount() == 0
                val user = User(
                    login = login, pass = pass, fullName = fullName,
                    birthDate = birthDate, gender = gender,
                    avatarUri = avatarUri, isAdmin = isFirstUser
                )
                db.addUser(user)

                buttonRegister.post {
                    buttonRegister.isEnabled = true
                    Log.d("RegistrationActivity", "Зарегистрирован $login, admin=$isFirstUser")
                    Toast.makeText(
                        this,
                        if (isFirstUser) "Вы первый — вы администратор" else "Регистрация успешна",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }.start()
        }
    }

    private fun showDatePicker(target: EditText) {
        val c = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, y, m, d -> target.setText(String.format("%02d.%02d.%04d", d, m + 1, y)) },
            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val file = File(filesDir, "avatar_${System.currentTimeMillis()}.jpg")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        stream.close()
        return Uri.fromFile(file).toString()
    }
}