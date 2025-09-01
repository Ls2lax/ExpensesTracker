package com.example.expensestracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensestracker.data.dao.ExpenseDao
import com.example.expensestracker.data.dao.UserDao
import com.example.expensestracker.data.entities.Expense
import com.example.expensestracker.data.entities.User

@Database(entities = [Expense::class, User::class], version = 1)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getInstance(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ExpenseDatabase::class.java, "expensesdb").build()
                INSTANCE = instance
                instance
            }
        }

    }
}