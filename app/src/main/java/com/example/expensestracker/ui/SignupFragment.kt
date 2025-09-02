package com.example.expensestracker.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.expensestracker.R
import androidx.fragment.app.Fragment
import com.example.expensestracker.data.database.ExpenseDatabase
import com.example.expensestracker.data.entities.User
import com.example.expensestracker.databinding.FragmentSignupBinding
import com.example.expensestracker.repository.UserRepository
import com.example.expensestracker.viewmodels.UserViewModel

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var userViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        val userDao = ExpenseDatabase.getInstance(requireContext()).userDao()
        val userRepo = UserRepository(userDao)
        userViewModel = UserViewModel(userRepo)

        binding.signup.setOnClickListener {
            val email = binding.sEmail.text.toString()
            val name = binding.sName.text.toString()
            val password = binding.sPassword.text.toString();

            if(email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                Log.d("Laxman", email + name + password)
                userViewModel.registerUser(User(name = name, email =  email, password =  password),{success, message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    if(success) {
                        parentFragmentManager.popBackStack()
                    }
                })
            } else {
                Toast.makeText(requireContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.sLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}