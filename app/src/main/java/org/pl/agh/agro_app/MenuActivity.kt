package org.pl.agh.agro_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu2)
    }

    fun UVI(view: android.view.View) {
        startActivity(Intent(this, FieldData::class.java))
    }

    fun fieldSoilData(view: android.view.View) {
        startActivity(Intent(this, CreateFieldPerimeter::class.java))
    }

    fun plantStatus(view: android.view.View) {
        startActivity(Intent(this, WeatherData::class.java))
    }

    fun weatherData(view: android.view.View) {
        startActivity(Intent(this, PlantStatus::class.java))
    }

    fun createFieldPerimeter(view: android.view.View) {
        startActivity(Intent(this, FieldSoilData::class.java))
    }

    fun fieldsData(view: android.view.View) {
        startActivity(Intent(this, FieldUVI::class.java))
    }

}