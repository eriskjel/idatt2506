package edu.ntnu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TesterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tester)

        val button = findViewById<Button>(R.id.buttonStartActivity)
        button.setOnClickListener {
            val intent = Intent("edu.ntnu.RANDOM_NUMBER_GENERATOR")
            intent.putExtra("upper_limit", 100)
            startActivityForResult(intent, 1)
        }

        val button2 = findViewById<Button>(R.id.buttonGoToTask2)
        button2.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val randomValue = data?.getIntExtra("result", 0)
            val textView = findViewById<TextView>(R.id.textView)
            textView.text = "Random value: $randomValue"
        }
    }
}
