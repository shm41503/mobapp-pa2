package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class Activity2 : AppCompatActivity() {

    private lateinit var listViewRestaurants: ListView

    private val restaurantList = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)

        listViewRestaurants = findViewById(R.id.listViewRestaurants)
        loadRestaurants()

        listViewRestaurants.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val restaurant = restaurantList[position]
            val intent = Intent(this, Activity3::class.java)
            intent.putExtra("RESTAURANT_ID", restaurant.id)
            startActivity(intent)
        }
    }

    private fun loadRestaurants() {
        try {
            val inputStream = assets.open("restaurant_info.txt")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val name = jsonObject.getString("restaurant")
                val type = jsonObject.getString("type")
                val location = jsonObject.getString("location")
                val rating = jsonObject.getString("rating")
                val imageResource = resources.getIdentifier(jsonObject.getString("image"), "drawable", packageName)

                restaurantList.add(Restaurant(id, name, type, location, rating, imageResource))
            }

            val adapter = RestaurantAdapter(this, restaurantList)
            listViewRestaurants.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


