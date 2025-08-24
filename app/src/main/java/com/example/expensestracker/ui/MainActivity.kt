package com.example.expensestracker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.data.database.ExpenseDatabase
import com.example.expensestracker.data.entities.Expense
import com.example.expensestracker.repository.ExpenseRepository
import com.example.expensestracker.viewmodels.ExpenseViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var totalBalance: TextView
    private lateinit var totalExpense: TextView
    private lateinit var addExpense: Button
    private lateinit var showExpenses: Button

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var expenseRecyclerView: RecyclerView
    fun updateViewModel() {
        val db = ExpenseDatabase.getInstance(applicationContext)
        val repo = ExpenseRepository(db.expenseDao())
        expenseViewModel = ExpenseViewModel(repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updateViewModel()
        totalBalance = findViewById(R.id.total_balance)
        totalExpense = findViewById(R.id.total_expense)
        addExpense = findViewById(R.id.add_expense)
        showExpenses = findViewById(R.id.show_expenses)
        expenseRecyclerView = findViewById(R.id.expenses_recyclerview)
        addExpense.setOnClickListener {
            showAddExpenseDialog()
        }
    }
    private fun showAddExpenseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_expense, null)
        val etAmount = dialogView.findViewById<EditText>(R.id.amount)
        val etCategory = dialogView.findViewById<EditText>(R.id.category)
        val etNote = dialogView.findViewById<EditText>(R.id.note)
        val etDate = dialogView.findViewById<EditText>(R.id.date)
        val etType = dialogView.findViewById<EditText>(R.id.type)
        val btnSave = dialogView.findViewById<Button>(R.id.save)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnSave.setOnClickListener {
            val amount = etAmount.text.toString().toDouble()
            val category = etCategory.text.toString()
            val note = etNote.text.toString()
            val date = etDate.text.toString()
            val type = etType.text.toString()
            if(etAmount.text.toString().isNotEmpty() || category.isNotEmpty() || note.isNotEmpty() || date.isNotEmpty() || type.isNotEmpty()) {
                expenseViewModel.addExpense(Expense(category = category, amount = amount, note =  note, type =  type, date = date ))
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}


