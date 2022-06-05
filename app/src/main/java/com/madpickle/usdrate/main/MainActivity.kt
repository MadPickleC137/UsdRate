package com.madpickle.usdrate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.extensions.setSystemNavBarColor
import com.madpickle.usdrate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setSystemNavBarColor(R.color.purple_500)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

    }
}