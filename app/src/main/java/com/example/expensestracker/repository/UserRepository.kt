package com.example.expensestracker.repository

import android.provider.ContactsContract
import androidx.collection.emptyIntSet
import com.example.expensestracker.data.dao.UserDao
import com.example.expensestracker.data.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) = userDao.registerUser(user)

    suspend fun loginUser(email: String, password: String): User? = userDao.loginUser(email, password)
}