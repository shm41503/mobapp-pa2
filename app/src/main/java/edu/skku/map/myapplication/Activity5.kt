package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class Activity5 : AppCompatActivity() {

    private lateinit var imageViewRestaurantThumbnail: ImageView
    private lateinit var textViewRestaurantName: TextView
    private lateinit var textViewReservationDetails: TextView
    private lateinit var buttonCancel: Button

    private var restaurantId: Int = -1
    private var reservationId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity5)

        imageViewRestaurantThumbnail = findViewById(R.id.imageViewRestaurantThumbnail)
        textViewRestaurantName = findViewById(R.id.textViewRestaurantName)
        textViewReservationDetails = findViewById(R.id.textViewReservationDetails)
        buttonCancel = findViewById(R.id.buttonCancel)

        restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        reservationId = intent.getIntExtra("RESERVATION_ID", -1)
        loadReservationDetails()

        buttonCancel.setOnClickListener {
            cancelReservation()
        }
    }

    private fun loadReservationDetails() {
        val inputStream = assets.open("restaurant_info.txt")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val restaurant = jsonArray.getJSONObject(i)
            if (restaurant.getInt("id") == restaurantId) {
                val restaurantName = restaurant.getString("restaurant")
                val imageResource = resources.getIdentifier(restaurant.getString("image"), "drawable", packageName)

                imageViewRestaurantThumbnail.setImageResource(imageResource)
                textViewRestaurantName.text = restaurantName

                val reservationDetails = "People: ${intent.getIntExtra("PEOPLE", 0)}\n" +
                        "Date: ${intent.getStringExtra("DATE")}\n" +
                        "Time: ${intent.getStringExtra("TIME")}"
                textViewReservationDetails.text = reservationDetails

                break
            }
        }
    }

    private fun cancelReservation() {
        showToast("Reservation canceled.")

        // Navigate back to Activity1
        val intent = Intent(this, Activity1::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
