package com.example.expensestracker.repository

import androidx.lifecycle.LiveData
import com.example.expensestracker.data.dao.ExpenseDao
import com.example.expensestracker.data.entities.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun addExpense(expense: Expense) = expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)

    suspend fun getAllExpenses() = expenseDao.getAllExpenses()
}