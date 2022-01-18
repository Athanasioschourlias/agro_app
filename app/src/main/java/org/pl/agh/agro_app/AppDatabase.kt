package org.pl.agh.agro_app

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, UserPolygons::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun userPolygonsDao(): UserPolygonsDao
}
