package edu.ntnu

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var cardNumberEditText: EditText
    private lateinit var serverResponseTextView: TextView
    private lateinit var guessEditText: EditText
    private var guessAttempts = 0
    private val url = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp"


    private val client = OkHttpClient.Builder()
        .cookieJar(object : CookieJar {
            private val cookieStore = mutableMapOf<HttpUrl, List<Cookie>>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url] = cookies
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return cookieStore[url] ?: listOf()
            }
        })
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameEditText)
        cardNumberEditText = findViewById(R.id.cardNumberEditText)
        serverResponseTextView = findViewById(R.id.serverResponseTextView)
        guessEditText = findViewById(R.id.guessEditText)

        findViewById<Button>(R.id.startGameButton).setOnClickListener {
            guessAttempts = 0
            startNewGame()
        }

        findViewById<Button>(R.id.submitGuessButton).setOnClickListener {
            submitGuess()
        }
    }

    private fun startNewGame() {
        val name = URLEncoder.encode(nameEditText.text.toString(), "UTF-8")
        val cardNumber = URLEncoder.encode(cardNumberEditText.text.toString(), "UTF-8")
        val body = FormBody.Builder()
            .add("navn", name)
            .add("kortnummer", cardNumber)
            .build()
        val request = Request.Builder().url(url).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string() ?: "No response from server"
                runOnUiThread {
                    serverResponseTextView.text = responseData
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    private fun submitGuess() {
        val guess = guessEditText.text.toString()

        if (guessAttempts < 3) {
            guessAttempts++

            val body = FormBody.Builder()
                .add("tall", guess)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string() ?: "No response from server"
                    runOnUiThread {
                        serverResponseTextView.text = responseData
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
            })
        } else {
            Toast.makeText(this, "Ugyldig inndata eller for mange forsøk. Start et nytt spill.", Toast.LENGTH_SHORT).show()
            guessAttempts = 0
        }
    }

}