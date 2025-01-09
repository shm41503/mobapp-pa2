package edu.skku.map.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class RestaurantAdapter(private val context: Context, private val restaurants: List<Restaurant>) : BaseAdapter() {

    override fun getCount(): Int {
        return restaurants.size
    }

    override fun getItem(position: Int): Any {
        return restaurants[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity2items, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imageViewProfile)
        val textViewName = view.findViewById<TextView>(R.id.textRestaurantName)
        val textViewDetails = view.findViewById<TextView>(R.id.textViewRecentChat)

        val restaurant = restaurants[position]
        imageView.setImageResource(restaurant.imageResource)
        textViewName.text = restaurant.name
        textViewDetails.text = "${restaurant.type} / ${restaurant.rating} / ${restaurant.location}"

        return view
    }
}
