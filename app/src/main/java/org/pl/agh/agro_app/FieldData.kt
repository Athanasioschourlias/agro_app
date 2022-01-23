package org.pl.agh.agro_app

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_field_data.*
import kotlinx.android.synthetic.main.activity_weather_data.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import com.android.volley.toolbox.RequestFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class FieldData : AppCompatActivity() {

    // Instantiate the RequestQueue.
    lateinit var queue: RequestQueue
    lateinit var email: String
    private lateinit var apikey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_data)


        queue = Volley.newRequestQueue(this)
        apikey = intent.getStringExtra("apikey").toString()
        email = intent.getStringExtra("email").toString()

        Log.i(email, "log in")

        //Connection to our db
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "agrodb"
        ).allowMainThreadQueries()
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration()
            .build()

        val userDao = db.userDao()

        val uivArr = ArrayList<String>()
        val soilArr = ArrayList<String>()

        val userId = userDao.userExists(email)
        val polyIds = db.userPolygonsDao().getAllPolyIds()

        val urlUVI = "https://api.agromonitoring.com/agro/1.0/uvi?polyid=%s&appid=%s".format(
            polyIds[0],
            apikey
        )

        val urlSOIL = "https://api.agromonitoring.com/agro/1.0/soil?polyid=%s&appid=%s".format(
            polyIds[0],
            apikey
        )

        // Request a string response from the provided URL.
        val jsonObjectRequestUVI = JsonObjectRequest(
            Request.Method.GET, urlUVI, null,
            { response ->
                uvi.setText("Ultraviolet index: " + response.get("uvi").toString())
            },
            { error ->
                uvi.setText("Oupsssss We couldn't find the place you are looking for check the cordinates and try again")
            })



        // Request a string response from the provided URL.
        val jsonObjectRequestSOIL = JsonObjectRequest(
            Request.Method.GET, urlSOIL, null,
            { response ->
                soil.setText("Soil moisture, m3/m3: "+ response.get("moisture").toString())
                t0.setText("Surface temperature, Kelvins: " + response.get("t0").toString())
                t10.setText("Temperature on the 10 centimeters depth, Kelvins: " + response.get("t10").toString())
            },
            { error ->
                soil.setText("Oupsssss We couldn't find the place you are looking for check the cordinates and try again")
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequestSOIL)
        queue.add(jsonObjectRequestUVI)

    }
}