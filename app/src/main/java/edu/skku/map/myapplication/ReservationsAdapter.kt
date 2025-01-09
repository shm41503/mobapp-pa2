package edu.skku.map.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ReservationsAdapter(private val context: Context, private val reservations: List<UserInfoParser.Reservation>) : BaseAdapter() {

    override fun getCount(): Int {
        return reservations.size
    }

    override fun getItem(position: Int): Any {
        return reservations[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity1items, parent, false)

        val imageViewProfile = view.findViewById<ImageView>(R.id.imageViewProfile)
        val textRestaurantName = view.findViewById<TextView>(R.id.textRestaurantName)
        val textPeople = view.findViewById<TextView>(R.id.textPeople)
        val textDate = view.findViewById<TextView>(R.id.textDate)
        val textTime = view.findViewById<TextView>(R.id.textTime)

        val reservation = reservations[position]
        textRestaurantName.text = "Restaurant ID: ${reservation.restaurantId}" // Adjust this to display actual restaurant name
        textPeople.text = "People: ${reservation.numberOfPeople}"
        textDate.text = "Date: ${reservation.date}"
        textTime.text = "Time: ${reservation.time}"

        return view
    }
}

