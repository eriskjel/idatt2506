package com.example.oving6


import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    private val activity: MainActivity,
    private val textView: TextView,
    private val SERVER_IP: String = "10.0.2.2",
    private val SERVER_PORT: Int = 12345,
) {

    private val editTextInput: EditText = activity.findViewById(R.id.editTextInput)
    private val buttonSend: Button = activity.findViewById(R.id.buttonSend)

    /**
     * Egendefinert set() som gjør at vi enkelt kan endre teksten som vises i skjermen til
     * emulatoren med
     *
     * ```
     * ui = "noe"
     * ```
     */
    private var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = str }
            field = str
        }

    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            ui = "Kobler til tjener..."
            try {
                socket = Socket(SERVER_IP, SERVER_PORT)
                ui = "Koblet til tjener:\n$socket"

                reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                writer = PrintWriter(socket.getOutputStream(), true)

                // Start a coroutine to continuously listen for messages
                listenForMessages()

                // Continuously send input from the EditText field to the server
                while (true) {
                    val input = editTextInput.text.toString()
                    if (input.isNotBlank()) {
                        sendToServer(input)
                        editTextInput.text.clear()
                    }
                    delay(1000) // Adjust the delay as needed
                }

            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }


    private suspend fun listenForMessages() {
        while (true) {
            try {
                val message = reader.readLine()
                ui = "Melding fra tjeneren:\n$message"
            } catch (e: IOException) {
                e.printStackTrace()
                ui = "Error reading message from server: ${e.message}"
                break // Exit the loop on error
            }
        }
    }

    init {
        buttonSend.setOnClickListener {
            val messageToSend = editTextInput.text.toString()
            if (messageToSend.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    sendToServer(messageToSend)
                }
                editTextInput.text.clear()
            }
        }
    }

    fun sendToServer(message: String) {
        try {
            writer.println(message)
            ui = "Sendte følgende til tjeneren: \n\"$message\""
        } catch (e: Exception) {
            e.printStackTrace()
            ui = "Error sending message to server: ${e.message}"
        }
    }
}