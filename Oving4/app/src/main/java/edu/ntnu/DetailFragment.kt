package edu.ntnu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {
    lateinit var imageView: ImageView
    lateinit var descriptionView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        imageView = view.findViewById(R.id.item_image)
        descriptionView = view.findViewById(R.id.item_description)
        return view
    }

    fun showItemDetail(item: Item) {
        imageView.setImageResource(item.imageResId)
        descriptionView.text = item.description
    }
}