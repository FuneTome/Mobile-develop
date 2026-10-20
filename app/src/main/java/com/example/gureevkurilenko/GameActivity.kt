package com.example.gureevkurilenko

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil
import kotlin.math.log2

class GameActivity : AppCompatActivity() {

    private var targetNumber = 0
    private var attemptsLeft = 0

    private var minRange = 0
    private var maxRange = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val layoutSetup = findViewById<LinearLayout>(R.id.layoutSetup)
        val layoutGame = findViewById<LinearLayout>(R.id.layoutGame)

        val editMin = findViewById<EditText>(R.id.editMin)
        val editMax = findViewById<EditText>(R.id.editMax)
        val buttonStartGame = findViewById<Button>(R.id.buttonStartGame)

        val editGuess = findViewById<EditText>(R.id.editGuess)
        val buttonGuess = findViewById<Button>(R.id.buttonGuess)
        val textHint = findViewById<TextView>(R.id.textHint)
        val textAttempts = findViewById<TextView>(R.id.textAttempts)
        val textRange = findViewById<TextView>(R.id.textRange)

        val layoutEndButtons = findViewById<LinearLayout>(R.id.layoutEndButtons)
        val buttonRestart = findViewById<Button>(R.id.buttonRestart)
        val buttonExit = findViewById<Button>(R.id.buttonExit)

        buttonStartGame.setOnClickListener {
            val min = editMin.text.toString().toIntOrNull()
            val max = editMax.text.toString().toIntOrNull()

            if (min == null || max == null) {
                Toast.makeText(this, "Введите корректные целые числа", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (min < 0 || max < 0) {
                Toast.makeText(this, "Отрицательные числа запрещены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (min >= max) {
                Toast.makeText(this, "Максимум должен быть больше минимума", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            minRange = min
            maxRange = max

            val range = maxRange - minRange + 1
            attemptsLeft = ceil(log2(range.toDouble())).toInt()
            if (attemptsLeft == 0) attemptsLeft = 1

            targetNumber = (minRange..maxRange).random()

            textRange.text = "Диапазон: от $minRange до $maxRange"
            layoutSetup.visibility = View.GONE
            layoutGame.visibility = View.VISIBLE

            // Сбрасываем кнопки в исходное состояние
            buttonGuess.visibility = View.VISIBLE
            layoutEndButtons.visibility = View.GONE
            editGuess.isEnabled = true
            textHint.text = "Введи свою догадку:"
            editGuess.text.clear()

            updateAttemptsText(textAttempts)
        }

        buttonGuess.setOnClickListener {
            val guess = editGuess.text.toString().toIntOrNull()
            if (guess == null) {
                Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            editGuess.text.clear()

            if (guess == targetNumber) {
                textHint.text = "БИНГО! Ты угадал число $targetNumber!"
                finishGame(buttonGuess, layoutEndButtons, editGuess)
                Toast.makeText(this, "Победа!", Toast.LENGTH_LONG).show()
            } else {
                attemptsLeft--
                vibratePhone()

                if (attemptsLeft <= 0) {
                    textHint.text = "Попытки кончились. Было загадано: $targetNumber"
                    finishGame(buttonGuess, layoutEndButtons, editGuess)
                } else {
                    textHint.text = if (guess < targetNumber) "Загаданное число БОЛЬШЕ" else "Загаданное число МЕНЬШЕ"
                }
            }
            updateAttemptsText(textAttempts)
        }

        buttonRestart.setOnClickListener {
            layoutGame.visibility = View.GONE
            layoutSetup.visibility = View.VISIBLE
            editMin.text.clear()
            editMax.text.clear()
        }

        buttonExit.setOnClickListener {
            finish()
        }
    }

    private fun finishGame(buttonGuess: Button, layoutEndButtons: LinearLayout, editGuess: EditText) {
        buttonGuess.visibility = View.GONE
        editGuess.isEnabled = false
        layoutEndButtons.visibility = View.VISIBLE
    }

    private fun updateAttemptsText(textView: TextView) {
        textView.text = "Осталось попыток: $attemptsLeft"
    }

    private fun vibratePhone() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(300)
        }
    }
}