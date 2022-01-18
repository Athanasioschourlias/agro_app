package org.pl.agh.agro_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun logInUser(view: android.view.View) {

        if( mail_input.text.isNullOrEmpty() )
            return

        //Connection to our db
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "agrodb"
        ).allowMainThreadQueries()
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration()
            .build()

        val userDao = db.userDao()

        val userCount: Int = userDao.getCountOfUsers()

        //Users info
        val mail = mail_input.text.toString()
        var username = name_input.text.toString()

        //Searching if there is any matches for a user with this mail!
        if(userDao.userExists(mail) != 0) {
            username = userDao.getUserById(userDao.userExists(mail)).userName.toString()
            mail_input.setText("")
            name_input.setText("")
            startActivity(Intent(this, MenuActivity::class.java).putExtra("logedInUser", username))
            return
        }


        //Creating a user if none exists in db


        //Cleaning the log-in screen
        mail_input.setText("")
        name_input.setText("")

        //Creating a name for the user if none is given
        if(username.isEmpty())
            username = "user" + (userCount + 1).toString()

        //Inserting the new user to the db
        userDao.insertAll(User(Email = mail, userName = username))

        startActivity(Intent(this, MenuActivity::class.java).putExtra("logedInUser", username))
    }

    fun guestUser(view: android.view.View) {
        startActivity(Intent(this, MenuActivity::class.java).putExtra("GuestUser", "Guest"))
    }
}