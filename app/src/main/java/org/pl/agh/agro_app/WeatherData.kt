package org.pl.agh.agro_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_weather_data.*
import org.json.JSONObject

class WeatherData : AppCompatActivity() {

    // Instantiate the RequestQueue.
    lateinit var queue:RequestQueue
    private lateinit var apikey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_data)

        queue = Volley.newRequestQueue(this)
        apikey = intent.getStringExtra("apikey").toString()

    }

    fun getWeatherReport(view: android.view.View) {

        //Check if the user provided the necessary info!
        val lat = latitudeEdit.text
        val log = longitudeEdit.text

        if(lat.isNullOrEmpty() || log.isNullOrEmpty())
            return

        val url = "https://api.agromonitoring.com/agro/1.0/weather?lat=%s&lon=%s&appid=%s".format(lat,log,apikey)

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val weatherJson: JSONObject = response.getJSONArray("weather").get(0) as JSONObject
//                val mainJson: JSONObject = response.getJSONObject("main") as JSONObject
//                val windJson: JSONObject = response.getJSONObject("wind") as JSONObject
//                val cloudsJson: JSONObject = response.getJSONObject("clouds") as JSONObject
                main.setText(weatherJson.get("main").toString())
            },
            { error ->
                main.setText("Oupsssss We couldn't find the place you are looking for check the cordinates and try again")
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }


}