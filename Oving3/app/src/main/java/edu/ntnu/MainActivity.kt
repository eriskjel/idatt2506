package edu.ntnu

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : Activity() {

    private lateinit var friends: ArrayList<Friend>
    private lateinit var adapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize your ArrayList and Adapter
        friends = ArrayList()
        adapter = FriendAdapter(this, friends)

        // Find your ListView and attach the adapter
        val listView: ListView = findViewById(R.id.list_view)
        listView.adapter = adapter

        // Find your EditText fields
        val nameEditText: EditText = findViewById(R.id.editTextName)
        val birthdayEditText: EditText = findViewById(R.id.editTextBirthday)

        // Find your button and set its onClickListener
        val addButton: Button = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val birthday = birthdayEditText.text.toString()
            // Validate the inputs (optional)
            if (name.isNotBlank() && birthday.isNotBlank()) {
                // Create a new Friend object and add it to your ArrayList
                val newFriend = Friend(name, birthday)
                friends.add(newFriend)
                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged()

                // Optionally, clear the input fields
                nameEditText.text.clear()
                birthdayEditText.text.clear()
            }
        }

        // Add click listener for list items (for future editing functionality)
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedFriend = friends[position]
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_edit_friend, null, false)


            val editName: EditText = dialogView.findViewById(R.id.dialogNameEditText)
            val editBirthday: EditText = dialogView.findViewById(R.id.dialogBirthdayEditText)

            editName.setText(selectedFriend.name)
            editBirthday.setText(selectedFriend.birthday)

            dialogBuilder.setView(dialogView)
            dialogBuilder.setPositiveButton("Update") { _, _ ->
                selectedFriend.name = editName.text.toString()
                selectedFriend.birthday = editBirthday.text.toString()
                adapter.notifyDataSetChanged()
            }
            dialogBuilder.setNegativeButton("Cancel", null)
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }
}
