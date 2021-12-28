package com.weather.tempraturefind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.weather.tempraturefind.R
import com.google.gson.Gson
import com.weather.tempraturefind.moduls.weather

class MainActivity : AppCompatActivity() {
    val apiKey = "4f1fa58334139ba45c17b11c00d883e2"
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.searchButton)

        button.setOnClickListener {
            getWeather()
            findViewById<EditText>(R.id.searchInput).text.clear()
        }
    }
    private fun getWeather(){
        val searchInput = findViewById<EditText>(R.id.searchInput)
        try {
            if (searchInput.text.isNotEmpty()){
                var fullURL =
                "https://api.openweathermap.org/data/2.5/weather?q=${searchInput.text}&units=metric&appid=$apiKey"
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(
                    Request.Method.POST,fullURL,{ response ->
                        var results = gson.fromJson(response,weather::class.java)
                        findViewById<TextView>(R.id.temp).text = results.main.temp.toInt().toString()+" C"
                        findViewById<TextView>(R.id.status).text = results.weather[0].main
                        findViewById<TextView>(R.id.address).text = results.name+","+results.sys.country
                        findViewById<TextView>(R.id.temp_min).text = "Min Temp"+results.main.temp_min+" ْC"
                        findViewById<TextView>(R.id.temp_max).text = "Max Temp"+results.main.temp_max+" ْC"
                    },{
                        Toast.makeText(this,"Some thing wrong", Toast.LENGTH_SHORT).show()
                        println(it.message)
                    }
                )
                queue.add(stringRequest)
            }else{
                Toast.makeText(this,"Type something", Toast.LENGTH_SHORT).show()
            }
        }catch (err:Exception){

        }
    }
}