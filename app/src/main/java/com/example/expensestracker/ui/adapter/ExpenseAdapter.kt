package com.example.expensestracker.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.entities.Expense
import com.example.expensestracker.databinding.ItemExpenseBinding

class ExpenseAdapter(
    var expenseList: List<Expense>,
    val onclick: (Expense) -> Unit
): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ExpenseViewHolder,
        position: Int
    ) {
        holder.bind(expenseList[position])
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    fun updateExpenseList(newExpenseList: List<Expense>) {
        expenseList = newExpenseList
        notifyDataSetChanged()
    }


    inner class ExpenseViewHolder(val binding: ItemExpenseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.itemDate.text = expense.date
            binding.itemNote.text = expense.note
            binding.itemAmount.text = expense.amount.toString()
            if(expense.type == "Expense") {
                binding.itemAmount.setTextColor(Color.GREEN)
            } else {
                binding.itemAmount.setTextColor(Color.RED)
            }
        }
    }
}