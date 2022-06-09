package com.madpickle.usdrate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.extensions.setSystemNavBarColor
import com.madpickle.usdrate.core.view.NotificationBuilder
import com.madpickle.usdrate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSystemNavBarColor(R.color.purple_500)
        setContentView(binding.root)
        initNavController()
        initNotification()
    }

    private fun initNotification() {
        NotificationBuilder.createNotificationChannel(application)
    }

    private fun initNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun setProgressVisible(isVisible: Boolean){
        binding.progressBar.isVisible = isVisible
    }

    override
    fun onSupportNavigateUp() = navController.navigateUp()
}