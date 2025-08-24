package com.example.expensestracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensestracker.data.entities.Expense

@Dao
interface ExpenseDao {

    @Insert
    abstract fun insertExpense(expense: Expense)

    @Update
    abstract fun updateExpense(expense: Expense)

    @Query("select * from expenses")
    abstract fun getAllExpenses(): LiveData<List<Expense>>
}