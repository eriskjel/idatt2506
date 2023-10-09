package edu.ntnu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class CalculatorActivity : AppCompatActivity() {
    private val REQUEST_CODE_GENERATE_RANDOM_NUMBER = 1
    private lateinit var firstNumberTextView: TextView
    private lateinit var secondNumberTextView: TextView
    private lateinit var userAnswerEditText: EditText
    private lateinit var upperLimitEditText: EditText
    private lateinit var addButton: Button
    private lateinit var multiplyButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        firstNumberTextView = findViewById(R.id.n1)
        secondNumberTextView = findViewById(R.id.n2)
        upperLimitEditText = findViewById(R.id.upperLimitInput)
        userAnswerEditText = findViewById(R.id.answerInput)
        addButton = findViewById(R.id.button)
        multiplyButton = findViewById(R.id.button2)

        addButton.setOnClickListener {
            checkAnswer('+')
        }

        multiplyButton.setOnClickListener {
            checkAnswer('*')
        }
    }

    private fun checkAnswer(operation: Char) {
        try {
            val firstNumberStr = firstNumberTextView.text.toString()
            val secondNumberStr = secondNumberTextView.text.toString()
            val userAnswerStr = userAnswerEditText.text.toString()
            val upperLimitStr = upperLimitEditText.text.toString()

            // Log or print the values if needed
            // Log.d("Debug", "First Number: $firstNumberStr")

            val firstNumber = firstNumberStr.toInt()
            val secondNumber = secondNumberStr.toInt()
            val userAnswer = userAnswerStr.toInt()
            val upperLimit = upperLimitStr.toInt()

            val correctAnswer = when (operation) {
                '+' -> firstNumber + secondNumber
                '*' -> firstNumber * secondNumber
                else -> 0
            }

            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "Riktig!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Feil. Riktig svar er $correctAnswer", Toast.LENGTH_SHORT).show()
            }

            setRandomNumbers()
        } catch (e: NumberFormatException) {
            // Handle the exception
            Toast.makeText(this, "Invalid input, please enter a number.", Toast.LENGTH_SHORT).show()
        }
    }


    /*private fun setRandomNumbers() {
        val upperLimit = upperLimitEditText.text.toString().toInt()
        val firstNumber = Random.nextInt(upperLimit)
        val secondNumber = Random.nextInt(upperLimit)

        firstNumberTextView.text = firstNumber.toString()
        secondNumberTextView.text = secondNumber.toString()
    }*/

    private fun setRandomNumbers(){
        val upperLimit = upperLimitEditText.text.toString().toInt()
        val intent = Intent("edu.ntnu.RANDOM_NUMBER_GENERATOR")
        intent.putExtra("upper_limit", upperLimit)
        startActivityForResult(intent, REQUEST_CODE_GENERATE_RANDOM_NUMBER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GENERATE_RANDOM_NUMBER && resultCode == Activity.RESULT_OK) {
            val firstNumber = data?.getIntExtra("result", 0) ?: 0
            val secondNumber = data?.getIntExtra("result", 0) ?: 0 // For now, using the same number for the example

            Log.d("CalculatorActivity", "Received random number: $firstNumber")


            firstNumberTextView.text = firstNumber.toString()
            secondNumberTextView.text = secondNumber.toString()
        }
    }


}
