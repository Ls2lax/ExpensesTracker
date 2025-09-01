package com.example.expensestracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensestracker.data.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registerUser(user: User)

    @Query("select * from users where email = :email and password = :password limit 1")
    fun loginUser(email: String, password: String): User?
}