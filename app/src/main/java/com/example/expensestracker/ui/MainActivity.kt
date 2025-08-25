package com.example.expensestracker.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.data.database.ExpenseDatabase
import com.example.expensestracker.data.entities.Expense
import com.example.expensestracker.databinding.ActivityMainBinding
import com.example.expensestracker.databinding.DialogExpenseBinding
import com.example.expensestracker.repository.ExpenseRepository
import com.example.expensestracker.ui.adapter.ExpenseAdapter
import com.example.expensestracker.viewmodels.ExpenseViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var expenseAdapter: ExpenseAdapter
    fun updateViewModel() {
        val db = ExpenseDatabase.getInstance(applicationContext)
        val repo = ExpenseRepository(db.expenseDao())
        expenseViewModel = ExpenseViewModel(repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateViewModel()

        binding.addExpense.setOnClickListener {
            showAddExpenseDialog()
        }
        expenseAdapter = ExpenseAdapter(listOf(),{expense->
        })
        binding.expensesRecyclerview.adapter = expenseAdapter
        binding.expensesRecyclerview.layoutManager = LinearLayoutManager(this)
        expenseViewModel.allExpenses.observe(this) { expenseList ->
            expenseAdapter.updateExpenseList(expenseList)
        }
    }
    private fun showAddExpenseDialog() {
        val dialogExpenseBinding = DialogExpenseBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogExpenseBinding.root)
            .create()

        dialogExpenseBinding.save.setOnClickListener {
            val amount = dialogExpenseBinding.amount.text.toString()
            val category = dialogExpenseBinding.category.text.toString()
            val note = dialogExpenseBinding.note.text.toString()
            val date = dialogExpenseBinding.date.text.toString()
            val type = dialogExpenseBinding.type.text.toString()
            if(amount.isNotEmpty() || category.isNotEmpty() || note.isNotEmpty() || date.isNotEmpty() || type.isNotEmpty()) {
                expenseViewModel.addExpense(Expense(category = category, amount = amount.toDouble(), note =  note, type =  type, date = date ))
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}


