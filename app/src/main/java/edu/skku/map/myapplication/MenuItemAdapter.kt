package edu.skku.map.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MenuItemAdapter(private val context: Context, private val menuItems: List<MenuItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return menuItems.size
    }

    override fun getItem(position: Int): Any {
        return menuItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity3items, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imageViewProfile)
        val textViewName = view.findViewById<TextView>(R.id.textRestaurantName)
        val textViewPrice = view.findViewById<TextView>(R.id.textViewLastTime)

        val menuItem = menuItems[position]
        imageView.setImageResource(menuItem.imageResource)
        textViewName.text = menuItem.name
        textViewPrice.text = "${menuItem.price} 원"

        return view
    }
}
