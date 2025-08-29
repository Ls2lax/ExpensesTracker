package com.example.expensestracker.ui.adapter

import android.graphics.Color
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.expensestracker.data.entities.Expense
import com.example.expensestracker.data.entities.ExpenseType
import com.example.expensestracker.databinding.ItemExpenseBinding
import java.util.Date
import java.util.Locale

class ExpenseAdapter(
    var expenseList: List<Expense>,
    val onEdit: (Expense) -> Unit,
    val onDelete: (Expense) -> Unit
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
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.itemDate.text = sdf.format(Date(expense.date))
            binding.itemNote.text = expense.note
            binding.itemAmount.text = expense.amount.toString()
            if(expense.type == ExpenseType.INCOME) {
                binding.itemAmount.setTextColor(Color.GREEN)
            } else {
                binding.itemAmount.setTextColor(Color.RED)
            }
            binding.edit.isVisible = false
            binding.delete.isVisible = false
            binding.item.setOnClickListener {
                binding.edit.isVisible = !binding.edit.isVisible
                binding.delete.isVisible = !binding.delete.isVisible
            }
            binding.edit.setOnClickListener {
                onEdit(expense)
            }
            binding.delete.setOnClickListener {
                onDelete(expense)
            }
        }
    }
}