package org.pl.agh.agro_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserPolygonsDao {
    @Query("SELECT * FROM polygons")
    fun getAll(): List<UserPolygons>

    @Query("SELECT polygonId FROM polygons")
    fun getAllPolyIds():List<String>

    @Insert
    fun insertAll(vararg userPolygons: UserPolygons)

    @Delete
    fun delete(userPolygons: UserPolygons)
}