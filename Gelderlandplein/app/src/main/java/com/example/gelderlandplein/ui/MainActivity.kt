package com.example.gelderlandplein.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gelderlandplein.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setSupportActionBar(findViewById(R.id.toolbar))
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Test"

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navController = findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_arts, menu)
        switchMenu()
        return true
    }

    private fun switchMenu() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.homeFragment)) {
                toolbar.isVisible = false
            } else if (destination.id in arrayOf(R.id.SearchFragment)){
                toolbar.title = "Search"
                toolbar.isVisible = true
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else if (destination.id in arrayOf(R.id.ArtOverviewFragment)){
                toolbar.title = "Arts"
                toolbar.isVisible = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else if (destination.id in arrayOf(R.id.EventFragment)){
                toolbar.title = "Events"
                toolbar.isVisible = true
            }
        }
    }
}