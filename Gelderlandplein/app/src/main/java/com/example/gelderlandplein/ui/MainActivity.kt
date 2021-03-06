package com.example.gelderlandplein.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.widget.Button
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gelderlandplein.R
import com.example.gelderlandplein.ui.search.SearchListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.viniciusmo.keyboardvisibility.keyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setSupportActionBar(findViewById(R.id.toolbar))
        setSupportActionBar(toolbar)
        toolbar.isVisible = false
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
                    toolbar_title.text = getString(R.string.search)
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = true
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)

                    val searchItem = menu.findItem(R.id.btSearch)
                    val searchView = searchItem?.actionView as SearchView
//                    searchView.isIconifiedByDefault = false    <--- makes searchbar expanded by default if needed

                    keyboard{
                        onOpened {
                            toolbar_title.text = ""
                        }
                        onClosed {
                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                toolbar_title.text = getString(R.string.search)
                                searchView.clearFocus()
                                searchView.isIconified = true
                            }, 50)
                        }
                    }

                }
                in arrayOf(R.id.shopDetailFragment) -> {
                    toolbar_title.text = ""
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = true
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                in arrayOf(R.id.mapRouteFragment) -> {
                    toolbar_title.text = ""
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                in arrayOf(R.id.ArtOverviewFragment) -> {
                    toolbar_title.text = getString(R.string.art)
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                in arrayOf(R.id.ArtDetailFragment) -> {
                    toolbar_title.text = getString(R.string.art)
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                in arrayOf(R.id.EventFragment) -> {
                    toolbar_title.text = getString(R.string.news_and_events)
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                in arrayOf(R.id.eventDetailFragment) -> {
                    toolbar_title.text = getString(R.string.news_and_events)
                    toolbar.isVisible = true
                    toolbar.menu.findItem(R.id.btSearch)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_start_nav)?.isVisible = false
                    toolbar.menu.findItem(R.id.btn_website)?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                else -> {
                    toolbar.isVisible = false
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
