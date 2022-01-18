package org.pl.agh.agro_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


    @Dao
    interface UserDao {
        @Query("SELECT * FROM user")
        fun getAll(): List<User>

        @Query("SELECT * FROM user WHERE userId IN (:userIds)")
        fun loadAllByIds(userIds: IntArray): List<User>

        @Query("SELECT COUNT(userId) FROM user")
        fun getCountOfUsers(): Int

        @Query("SElECT userId FROM user WHERE user_Email = (:userEmail)")
        fun userExists(userEmail: String): Int

        @Query("SELECT * FROM user WHERE userId = (:userId)")
        fun getUserById(userId: Int): User

        @Insert
        fun insertAll(vararg users: User)

        @Delete
        fun delete(user: User)
    }