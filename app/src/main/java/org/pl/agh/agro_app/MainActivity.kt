package org.pl.agh.agro_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun logInUser(view: android.view.View) {
        //check if the user entered all the necessary info(e-mail) and if no username autogenerate one! user-001.....
        //Check if the user exists, if not create one
        startActivity(Intent(this, menu_activity::class.java))
    }

    fun guestUser(view: android.view.View) {
        startActivity(Intent(this, menu_activity::class.java))
    }
}