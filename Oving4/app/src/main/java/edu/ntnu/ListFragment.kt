package edu.ntnu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {

    lateinit var listView: ListView
    var listener: ItemSelectedListener? = null

    interface ItemSelectedListener {
        fun onItemSelected(item: Item)
        fun getItemList(): List<Item>
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemSelectedListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        listView = view.findViewById(R.id.item_list)

        val items = listener?.getItemList() ?: emptyList()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items.map { it.title })
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            listener?.onItemSelected(items[position])
        }
        return view
    }
}