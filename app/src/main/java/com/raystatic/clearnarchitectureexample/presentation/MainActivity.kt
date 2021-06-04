package com.raystatic.clearnarchitectureexample.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raystatic.clearnarchitectureexample.R
import com.raystatic.clearnarchitectureexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("rahulray: main")

    }
}