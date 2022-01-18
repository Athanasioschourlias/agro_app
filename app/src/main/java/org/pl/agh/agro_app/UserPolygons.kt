package org.pl.agh.agro_app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("userId"),
    childColumns = arrayOf("users_id"),
    onDelete = ForeignKey.CASCADE
)),
tableName = "polygons")
data class UserPolygons(
    @PrimaryKey(autoGenerate = true) val fieldId: Int,
    @ColumnInfo(name = "users_id")val users_id: Int,
    @ColumnInfo(name = "polygonId")val polygonId: String
){
    constructor(users_id: Int, polygonId: String) :
            this(0,users_id,polygonId)
}
