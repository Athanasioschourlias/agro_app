package org.pl.agh.agro_app

import android.graphics.drawable.Drawable
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
import java.util.regex.Matcher
import java.util.regex.Pattern

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

                var mydrawable: Drawable?

                if(weatherJson.get("main").toString().equals("Clouds")){
                    mydrawable = getResources().getDrawable(R.drawable.cloudy_day)
                }else if(weatherJson.get("main").toString().equals("Rain")){
                    mydrawable = getResources().getDrawable(R.drawable.cloudy_rain)
                }else if(weatherJson.get("main").toString().equals("Clear") || weatherJson.get("main").toString().equals("Sunny")){
                    mydrawable = getResources().getDrawable(R.drawable.sun)
                }else if(weatherJson.get("main").toString().equals("Snow")){
                    mydrawable = getResources().getDrawable(R.drawable.snow)
                }else if(weatherJson.get("main").toString().equals("Extreme")){
                    mydrawable = getResources().getDrawable(R.drawable.storm)
                }else{
                    mydrawable = getResources().getDrawable(R.drawable.season)
                }

                main
                    .setCompoundDrawablesRelativeWithIntrinsicBounds(
                        mydrawable,
                        null,
                        null,
                        null
                    )


                main.setText("Overall situation: " + weatherJson.get("main").toString())

                description.setText("Main weather Description: " + weatherJson.get("description").toString())
                val mainJson: JSONObject = response.getJSONObject("main") as JSONObject
                tempreture.setText("Temperature: " + mainJson.get("temp").toString() + " Kelvin")
                tempretureMin.setText("Minimum Temperature: " + mainJson.get("temp_min").toString() + " Kelvin")
                tempretureMax.setText("Maximum Temperature: " + mainJson.get("temp_max").toString() + " Kelvin")
                feelsLike.setText("Feels like: " + mainJson.get("feels_like").toString()  + " Kelvin")
                humidity.setText("Humidity: " + mainJson.get("humidity").toString() + "%")
                pressure.setText("Atmospheric pressure: " + mainJson.get("pressure").toString() + "hPa")

                val windJson: JSONObject = response.getJSONObject("wind") as JSONObject
                windSpeed.setText("Wind speed: " +windJson.get("speed").toString() + " Meter/sec")
            },
            { error ->
                main.setText("Oupsssss We couldn't find the place you are looking for check the cordinates and try again")
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }


}