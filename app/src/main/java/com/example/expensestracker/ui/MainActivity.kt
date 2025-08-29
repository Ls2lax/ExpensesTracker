package com.example.expensestracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.R
import com.example.expensestracker.data.database.ExpenseDatabase
import com.example.expensestracker.data.entities.Expense
import com.example.expensestracker.data.entities.ExpenseType
import com.example.expensestracker.databinding.ActivityMainBinding
import com.example.expensestracker.databinding.DialogExpenseBinding
import com.example.expensestracker.repository.ExpenseRepository
import com.example.expensestracker.ui.adapter.ExpenseAdapter
import com.example.expensestracker.viewmodels.ExpenseViewModel
import kotlin.math.exp

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
            showEditExpenseDialog(expense)
        },{expense ->
            expenseViewModel.deleteExpense(expense)
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
            val checkedButtonId = dialogExpenseBinding.toggleButtonGroup.checkedButtonId
            val type: ExpenseType
            if(checkedButtonId == dialogExpenseBinding.ExpenseButton.id) {
                type = ExpenseType.EXPENSE
            } else {
                type = ExpenseType.INCOME
            }
            if(amount.isNotEmpty() || category.isNotEmpty() || note.isNotEmpty() || date.isNotEmpty() ) {
                expenseViewModel.addExpense(Expense(category = category, amount = amount.toDouble(), note =  note, type =  type, date = date ))
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    private fun showEditExpenseDialog(expense: Expense) {
        val dialogExpenseBinding = DialogExpenseBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogExpenseBinding.root)
            .create()

        dialogExpenseBinding.amount.setText(expense.amount.toString())
        dialogExpenseBinding.note.setText(expense.note)
        dialogExpenseBinding.category.setText(expense.category)
        dialogExpenseBinding.date.setText(expense.date)

        dialogExpenseBinding.save.setOnClickListener {
            val amount = dialogExpenseBinding.amount.text.toString()
            val category = dialogExpenseBinding.category.text.toString()
            val note = dialogExpenseBinding.note.text.toString()
            val date = dialogExpenseBinding.date.text.toString()
            val checkedButtonId = dialogExpenseBinding.toggleButtonGroup.checkedButtonId
            val type: ExpenseType
            if(checkedButtonId == dialogExpenseBinding.ExpenseButton.id) {
                type = ExpenseType.EXPENSE
            } else {
                type = ExpenseType.INCOME
            }
            if(amount.isNotEmpty() || category.isNotEmpty() || note.isNotEmpty() || date.isNotEmpty()){
                expense.amount = amount.toDouble()
                expense.type = type
                expense.note = note
                expense.date = date
                expense.category =category
                expenseViewModel.updateExpense(expense)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

}


