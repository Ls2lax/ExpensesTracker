package com.example.expensestracker.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expensestracker.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Laxman", "Inside AuthActivity")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPref = getSharedPreferences("expenseApp", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)
        if(userId != -1) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        setContentView(R.layout.activity_auth)
        if(savedInstanceState == null) {
            Log.d("Laxman", "starting login fragment")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragement())
                .commit()
        }
    }
}