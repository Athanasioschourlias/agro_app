package org.pl.agh.agro_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View



class MenuActivity : AppCompatActivity() {

    lateinit var username: String
    private val apiKey: String = "df2b16b8ba5b0e011af2bef6b443e53c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2)
        username = intent.getStringExtra("logedInUser").toString()

        if(!username.equals("null")) {

            Log.i(username, "log in")
            val button = findViewById<View>(R.id.my_fields)

            button.setVisibility(View.VISIBLE)

        }
    }

    fun UVI(view: android.view.View) {
        startActivity(Intent(this, FieldUVI::class.java).putExtra("apikey",apiKey))
    }

    fun fieldSoilData(view: android.view.View) {
        startActivity(Intent(this, FieldSoilData::class.java).putExtra("apikey",apiKey))
    }

    fun plantStatus(view: android.view.View) {
        startActivity(Intent(this, PlantStatus::class.java).putExtra("apikey",apiKey))
    }

    fun weatherData(view: android.view.View) {
        startActivity(Intent(this, WeatherData::class.java).putExtra("apikey",apiKey))
    }

    fun createFieldPerimeter(view: android.view.View) {
        startActivity(Intent(this, CreateFieldPerimeter::class.java).putExtra("apikey",apiKey))
    }

    fun fieldsData(view: android.view.View) {
        startActivity(Intent(this, FieldData::class.java).putExtra("apikey",apiKey))
    }

}