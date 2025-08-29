package com.example.expensestracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var category: String,
    @ColumnInfo var amount: Double,
    @ColumnInfo var note: String,
    @ColumnInfo var date: String,
    @ColumnInfo var type: ExpenseType
)
