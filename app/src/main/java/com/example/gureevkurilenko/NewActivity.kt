package com.example.gureevkurilenko

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class NewActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helloact)

        val editBirthDate = findViewById<EditText>(R.id.editBirthDate)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        editBirthDate.setOnClickListener {
            showDatePicker(editBirthDate)
        }

        buttonRegister.setOnClickListener {
            Log.d("RegistrationActivity", "Нажата кнопка Зарегистрироваться")
        }
    }

    private fun showDatePicker(target: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formatted = String.format(
                    "%02d.%02d.%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
                )
                target.setText(formatted)
                Log.d("RegistrationActivity", "Выбрана дата: $formatted")
            },
            year, month, day
        )

        dialog.show()
    }
}