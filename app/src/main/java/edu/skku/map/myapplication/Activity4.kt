package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class Activity4 : AppCompatActivity() {

    private lateinit var textViewOpeningHours: TextView
    private lateinit var editTextPeople: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var buttonConfirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity4)

        textViewOpeningHours = findViewById(R.id.textViewOpeningHours)
        editTextPeople = findViewById(R.id.editTextPeople)
        editTextDate = findViewById(R.id.editTextDate)
        editTextTime = findViewById(R.id.editTextTime)
        buttonConfirm = findViewById(R.id.buttonConfirm)

        val restaurantId = intent.getIntExtra("RESTAURANT_ID", -1)
        if (restaurantId != -1) {
            loadRestaurantDetails(restaurantId)
        }

        buttonConfirm.setOnClickListener {
            val intent = Intent(this, Activity5::class.java)
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
                val openingHours = restaurant.getJSONObject("openingHours")
                val open = openingHours.getString("open")
                val close = openingHours.getString("close")
                textViewOpeningHours.text = "Opening hours: $open ~ $close"
                break
            }
        }
    }
}
