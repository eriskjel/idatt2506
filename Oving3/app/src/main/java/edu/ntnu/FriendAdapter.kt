package edu.ntnu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FriendAdapter(context: Context, private val friends: ArrayList<Friend>) : ArrayAdapter<Friend>(context, 0, friends) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val currentFriend = getItem(position)

        val nameTextView: TextView = listItemView!!.findViewById(R.id.name_text)
        val birthdayTextView: TextView = listItemView.findViewById(R.id.birthday_text)

        nameTextView.text = currentFriend?.name
        birthdayTextView.text = currentFriend?.birthday

        return listItemView
    }
}
