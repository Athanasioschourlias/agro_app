package org.pl.agh.agro_app

import android.R.attr
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_polygon.*
import kotlinx.android.synthetic.main.activity_weather_data.*
import org.json.JSONObject
import org.json.JSONException

import android.R.attr.theme
import androidx.room.Room
import org.json.JSONArray







class CreatePolygonActivity : AppCompatActivity() {

    // Instantiate the RequestQueue.
    lateinit var queue: RequestQueue
    private lateinit var apikey: String
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_polygon)

        queue = Volley.newRequestQueue(this)
        apikey = intent.getStringExtra("apikey").toString()
        email = intent.getStringExtra("email").toString()


    }

    fun createPolygon(view: android.view.View) {

        var latC1 = corner1.text
        var lonC1 = corner2.text
        var latC2 = corner3.text
        var lonC2 = corner4.text
        var latC3 = corner5.text
        var lonC3 = corner6.text
        var latC4 = corner7.text
        var lonC4 = corner8.text

        //Connection to our db
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "agrodb"
        ).allowMainThreadQueries()
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration()
            .build()

        //checking if the user entered the minimum number of points
        if(
            latC1.isNullOrEmpty()
            || lonC1.isNullOrEmpty()
            ||latC2.isNullOrEmpty()
            ||lonC2.isNullOrEmpty()
            ||latC3.isNullOrEmpty()
            ||lonC3.isNullOrEmpty()
        )
        {
           return
        }

        val url = "http://api.agromonitoring.com/agro/1.0/polygons?appid=%s".format(apikey)

        // Post parameters
        // Form fields and values
        val mainObject = JSONObject()
        val geoJson = JSONObject()
        val propertiesJson = JSONObject()
        val geometryJson = JSONObject()

        try {
            geoJson.put("type", "Feature")
            geometryJson.put("type","Polygon")

            val point1 = JSONArray()
            point1.put(latC1.toString())
            point1.put(lonC1.toString())

            val point2 = JSONArray()
            point2.put(latC2.toString())
            point2.put(lonC2.toString())

            val point3 = JSONArray()
            point3.put(latC3.toString())
            point3.put(lonC3.toString())

            val point4 = JSONArray()
            point4.put(latC4.toString())
            point4.put(lonC4.toString())

            val arr = JSONArray()
            arr.put(point1)
            arr.put(point2)
            arr.put(point3)
            arr.put(point4)

            geometryJson.put("coordinates", arr)

            geoJson.put("properties", propertiesJson)
            geoJson.put("geometry", geometryJson)
            mainObject.put("geo_json", geoJson)
        } catch (e: JSONException) {
            Log.i(e.toString(), "log in")
            return
        }

        res.setText(mainObject.toString())

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, mainObject,
            { response ->
                // Process the json
                try {
                    val polyId: JSONObject = response.get("id") as JSONObject
                    res.text = "polygon id is: $polyId"
                    val userId = db.userDao().userExists(email)

                    db.userPolygonsDao().insertAll(UserPolygons(users_id = userId,polygonId = polyId.toString()))

                }catch (e:Exception){
                    res.text = "Exception: $e"
                }
            },
            { error ->
                res.setText("Oupsssss We couldn't find the place you are looking for check the cordinates and try again")
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }
}