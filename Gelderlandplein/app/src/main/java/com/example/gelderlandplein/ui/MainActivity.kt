package com.example.gelderlandplein.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gelderlandplein.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setSupportActionBar(findViewById(R.id.toolbar))
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Test"
        toolbar.title = ""

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navController = findNavController(R.id.fragment)
        supportActionBar?.setDisplayShowHomeEnabled(true);
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        switchMenu(menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun switchMenu(menu: Menu) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                in arrayOf(R.id.homeFragment) -> {
                    toolbar.isVisible = false
                }
                in arrayOf(R.id.SearchFragment) -> {
                    toolbar_title.text = ""
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = true
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)

                    val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
                    val searchItem = menu.findItem(R.id.btSearch)
                    val searchView = searchItem?.actionView as SearchView
                }
                in arrayOf(R.id.shopDetailFragment) -> {
                    toolbar_title.text = ""
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                in arrayOf(R.id.mapRouteFragment) -> {
                    toolbar_title.text = ""
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    // Even een snelle fix zodat je vanuit de detailpage niet terug kan.
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                in arrayOf(R.id.ArtOverviewFragment) -> {
                    toolbar_title.text = "Art"
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                in arrayOf(R.id.ArtDetailFragment) -> {
                    toolbar_title.text = "Art"
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                in arrayOf(R.id.EventFragment) -> {
                    toolbar_title.text = "News & Events"
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                in arrayOf(R.id.eventDetailFragment) -> {
                    toolbar_title.text = "News & Events"
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        when (item.itemId) {
////            android.R.id.home -> {
////                navController.navigate(androidx.navigation.R.id.action_gamesPlayedFragment2_to_gameFragment)
////                return true
////            }
//
//            else -> super.onOptionsItemSelected(item)
//
//        }
//        return true
//    }
}