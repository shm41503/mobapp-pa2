package edu.skku.map.myapplication

import org.json.JSONArray
import org.json.JSONObject

class UserInfoParser {
    data class Reservation(
        val reservationId: Int,
        val restaurantId: Int,
        val numberOfPeople: Int,
        val date: String,
        val time: String
    )

    fun parseUserInfo(jsonString: String): List<Reservation> {
        val reservations = mutableListOf<Reservation>()
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val reservedArray = jsonObject.getJSONArray("reserved")
            for (j in 0 until reservedArray.length()) {
                val reservedObject = reservedArray.getJSONObject(j)
                val reservation = Reservation(
                    reservedObject.getInt("reservation_id"),
                    reservedObject.getInt("restaurant_id"),
                    reservedObject.getInt("number_of_people"),
                    reservedObject.getString("date"),
                    reservedObject.getString("time")
                )
                reservations.add(reservation)
            }
        }

        return reservations
    }
}
