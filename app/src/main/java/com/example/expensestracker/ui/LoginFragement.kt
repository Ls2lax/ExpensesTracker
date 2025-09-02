package com.example.expensestracker.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensestracker.R
import com.example.expensestracker.data.database.ExpenseDatabase
import com.example.expensestracker.data.entities.User
import com.example.expensestracker.databinding.FragmentLoginFragementBinding
import com.example.expensestracker.repository.UserRepository
import com.example.expensestracker.viewmodels.UserViewModel


class LoginFragement : Fragment(R.layout.fragment_login_fragement) {

    private lateinit var binding: FragmentLoginFragementBinding
    private lateinit var userViewModel: UserViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginFragementBinding.bind(view)
        val userDao = ExpenseDatabase.getInstance(requireContext()).userDao()
        val userRepo = UserRepository(userDao)
        userViewModel = UserViewModel(userRepo)

        binding.login.setOnClickListener {
            val email = binding.lEmail.text.toString()
            val password = binding.lPassowod.text.toString()
            userViewModel.loginUser(email, password, {user ->
                if(user != null) {
                    saveUserSession(user)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Invalid Credential", Toast.LENGTH_SHORT).show()
                }
            })
        }
        binding.lSignup.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignupFragment()) // replace Login with Signup
                .addToBackStack(null) // so back button goes back to login
                .commit()
        }
    }
    fun saveUserSession(user: User) {
        val sharedRef = requireContext().getSharedPreferences("expenseApp", Context.MODE_PRIVATE)
        sharedRef.edit().putInt("userId", user.id).apply()
    }
}