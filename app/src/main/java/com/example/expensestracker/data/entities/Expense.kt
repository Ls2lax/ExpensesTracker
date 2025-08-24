package com.example.expensestracker.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val category: String,
    @ColumnInfo val amount: Double,
    @ColumnInfo val note: String?,
    @ColumnInfo val date: String,
    @ColumnInfo val type: String
)
