package org.pl.agh.agro_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_menu2.*


class MenuActivity : AppCompatActivity() {

    lateinit var username: String
    lateinit var userId: String
    private val apiKey: String = "df2b16b8ba5b0e011af2bef6b443e53c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2)
        username = intent.getStringExtra("logedInUser").toString()
        userId = intent.getStringExtra("userID").toString()

        if(!username.equals("null")) {

            val button = findViewById<View>(R.id.my_fields)
            button.setVisibility(View.VISIBLE)
            create_polygon.setVisibility(View.VISIBLE)
            my_fields.setVisibility(View.VISIBLE)
        }


    }

    fun weatherData(view: android.view.View) {
        startActivity(Intent(this, WeatherData::class.java).putExtra("apikey",apiKey))
    }

    fun createFieldPerimeter(view: android.view.View) {
        startActivity(Intent(this, CreatePolygonActivity::class.java).putExtra("apikey",apiKey))
    }

    fun fieldsData(view: android.view.View) {
        startActivity(Intent(this, FieldData::class.java).putExtra("apikey",apiKey))
    }

}