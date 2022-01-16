package org.pl.agh.agro_app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    @ColumnInfo(name = "user_Email") val userEmail: String,
    @ColumnInfo(name = "username") val userName: String?
){
    constructor(e_mail: String, userName: String?) :
            this(0,e_mail,userName)
}
