package com.example.expensestracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.data.entities.User
import com.example.expensestracker.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userRepository: UserRepository) : ViewModel(){

    fun registerUser(user: User, onResult:(Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                userRepository.registerUser(user)
                withContext(Dispatchers.Main) {
                    onResult(true, "Signup Successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(false, "Email already Exist")
                }
            }
        }
    }

    fun loginUser(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.loginUser(email, password)
            withContext(Dispatchers.Main) {
                onResult(user)
            }
        }
    }
}