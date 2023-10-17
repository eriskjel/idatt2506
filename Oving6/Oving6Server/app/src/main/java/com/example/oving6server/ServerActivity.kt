package com.example.oving6server


import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(private val activity: MainActivity, private val textView: TextView, private val PORT: Int = 12345) {

    private val editTextInput: EditText = activity.findViewById(R.id.editTextInput)
    private val buttonSend: Button = activity.findViewById(R.id.buttonSend)

    private var clientSocket: Socket? = null
    private var clientWriter: PrintWriter? = null




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

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ui = "Starter Tjener ..."
                ServerSocket(PORT).use {
                    ui = "ServerSocket opprettet, venter på at en klient kobler seg til...."

                    clientSocket = it.accept()
                    ui = "En Klient koblet seg til:\n$clientSocket"
                    // Send initial welcome message to the client
                    sendToClient("Velkommen Klient!")
                    // Start listening for client messages
                    listenForClientMessages()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                ui = e.message
            }
        }
    }



    init {
        buttonSend.setOnClickListener {
            val messageToSend = editTextInput.text.toString()
            if (messageToSend.isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    sendMessageToClient(messageToSend)
                }
                editTextInput.text.clear()
            }
        }
    }

    private fun listenForClientMessages() {
        try {
            val reader = BufferedReader(InputStreamReader(clientSocket?.getInputStream()))
            while (true) {
                val message = reader.readLine()
                if (message != null) {
                    ui = "Klienten sier:\n$message"
                } else {
                    // Handle client disconnection here, e.g., break the loop or take appropriate action.
                    break
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            ui = "Error reading message from client: ${e.message}"
        }
    }




    private fun readFromClient() {
        clientSocket?.let { socket ->
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val message = reader.readLine()
            ui = "Klienten sier:\n$message"
        }
    }

    private fun sendToClient(message: String) {
        if (clientWriter == null) {
            clientSocket?.let {
                clientWriter = PrintWriter(it.getOutputStream(), true)
            } ?: run {
                ui = "Failed to send message: clientSocket is null"
                return
            }
        }
        clientWriter?.println(message)
        ui = "Sendte følgende til klienten:\n$message"
    }

    fun sendMessageToClient(message: String) {
        try {
            clientSocket?.let {
                val writer = PrintWriter(it.getOutputStream(), true)
                writer.println(message)
                ui = "Message sent: $message"
            } ?: run {
                ui = "Failed to send message: clientSocket is null"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ui = "Error sending message: ${e.message}"
        }
    }

}