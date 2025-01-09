package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class Activity3 : AppCompatActivity() {

    private lateinit var imageViewRestaurantThumbnail: ImageView
    private lateinit var textViewRestaurantDetails: TextView
    private lateinit var listViewMenu: ListView
    private lateinit var buttonReservation: Button

    private val menuList = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3)

        imageViewRestaurantThumbnail = findViewById(R.id.imageViewRestaurantThumbnail)
        textViewRestaurantDetails = findViewById(R.id.textViewRestaurantDetails)
        listViewMenu = findViewById(R.id.listViewMenu)
        buttonReservation = findViewById(R.id.buttonReservation)

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        if (restaurantId != -1) {
            loadRestaurantDetails(restaurantId)
        }

        buttonReservation.setOnClickListener {
            val intent = Intent(this, Activity4::class.java)
            intent.putExtra("RESTAURANT_ID", restaurantId)
            startActivity(intent)
        }
    }

    private fun loadRestaurantDetails(restaurantId: Int) {
        val inputStream = assets.open("restaurant_info.txt")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val restaurant = jsonArray.getJSONObject(i)
            if (restaurant.getInt("id") == restaurantId) {
                val restaurantName = restaurant.getString("restaurant")
                val type = restaurant.getString("type")
                val location = restaurant.getString("location")
                val rating = restaurant.getString("rating")
                val openingHours = restaurant.getJSONObject("openingHours")
                val open = openingHours.getString("open")
                val close = openingHours.getString("close")
                val description = restaurant.getString("description")
                val imageResource = resources.getIdentifier(restaurant.getString("image"), "drawable", packageName)

                imageViewRestaurantThumbnail.setImageResource(imageResource)
                textViewRestaurantDetails.text = "$restaurantName\n$type\n$location\nRating: $rating\nOpen: $open - Close: $close\n$description"

                val menuArray = restaurant.getJSONArray("Menu")
                for (j in 0 until menuArray.length()) {
                    val menuItem = menuArray.getJSONObject(j)
                    val menuName = menuItem.getString("name")
                    val menuPrice = menuItem.getInt("price")
                    val menuImageResource = resources.getIdentifier(menuItem.getString("image"), "drawable", packageName)
                    menuList.add(MenuItem(menuImageResource, menuName, menuPrice))
                }

                val adapter = MenuItemAdapter(this, menuList)
                listViewMenu.adapter = adapter

                break
            }
        }
    }
}
