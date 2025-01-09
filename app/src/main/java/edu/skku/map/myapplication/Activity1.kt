package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class Activity1 : AppCompatActivity() {

    private lateinit var imageViewPlaceholder: ImageView
    private lateinit var textViewUserInfo: TextView
    private lateinit var listViewReservations: ListView
    private lateinit var buttonReservation: Button

    private val reservationList = mutableListOf<UserInfoParser.Reservation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)

        imageViewPlaceholder = findViewById(R.id.imageViewPlaceholder)
        textViewUserInfo = findViewById(R.id.textViewUserInfo)
        listViewReservations = findViewById(R.id.listViewReservations)
        buttonReservation = findViewById(R.id.buttonReservation)

        val userId = intent.getStringExtra("USER_ID")
        userId?.let {
            loadUserInfo(it)
        }

        buttonReservation.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserInfo(userId: String) {
        try {
            val inputStream = assets.open("user_info.txt")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                if (jsonObject.getString("id") == userId) {
                    val name = jsonObject.getJSONObject("info").getString("name")
                    val age = jsonObject.getJSONObject("info").getInt("age")
                    val gender = jsonObject.getJSONObject("info").getString("gender")
                    val reservations = jsonObject.getJSONArray("reserved")

                    textViewUserInfo.text = "User Info: $name ($age/$gender)"

                    for (j in 0 until reservations.length()) {
                        val reservation = reservations.getJSONObject(j)
                        val reservationId = reservation.getInt("reservation_id")
                        val restaurantId = reservation.getInt("restaurant_id")
                        val numberOfPeople = reservation.getInt("number_of_people")
                        val date = reservation.getString("date")
                        val time = reservation.getString("time")
                        reservationList.add(UserInfoParser.Reservation(reservationId, restaurantId, numberOfPeople, date, time))
                    }

                    val adapter = ReservationsAdapter(this, reservationList)
                    listViewReservations.adapter = adapter

                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
