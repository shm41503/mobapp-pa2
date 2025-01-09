package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class Activity0 : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity0)

        editTextName = findViewById(R.id.edit_text_name)
        editTextPassword = findViewById(R.id.edit_text_age)
        buttonLogin = findViewById(R.id.button_go_to_activity2)

        buttonLogin.setOnClickListener {
            val userId = editTextName.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidCredentials(userId, password)) {
                navigateToActivity1(userId)
            } else {
                showToast("Login failed. Please check your ID and password.")
            }
        }
    }

    private fun isValidCredentials(userId: String, password: String): Boolean {
        try {
            val inputStream = assets.open("user_info.txt")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                if (jsonObject.getString("id") == userId && jsonObject.getString("passwd") == password) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun navigateToActivity1(userId: String) {
        val intent = Intent(this, Activity1::class.java)
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        editTextName.text.clear()
        editTextPassword.text.clear()
    }
}
