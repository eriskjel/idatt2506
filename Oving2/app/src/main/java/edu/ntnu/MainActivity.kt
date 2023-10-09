package edu.ntnu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val upperLimit = intent.getIntExtra("upper_limit", 100)
        val value = (0..upperLimit).random()

        val returnIntent = Intent()
        returnIntent.putExtra("result", value)
        setResult(Activity.RESULT_OK, returnIntent)

        finish()
    }
}