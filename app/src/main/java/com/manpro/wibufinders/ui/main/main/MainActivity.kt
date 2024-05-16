package com.manpro.wibufinders.ui.main.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.manpro.wibufinders.R
import com.manpro.wibufinders.databinding.ActivityMainBinding
import com.jakewharton.threetenabp.AndroidThreeTen


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        // Mendapatkan NavController dari NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup BottomNavigationView dengan NavController
        val navView = binding.navView
        navView.setupWithNavController(navController)

        // Menambahkan listener untuk menangani item menu yang diklik
        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Navigasi ke tujuan "Home"
                    navController.navigate(R.id.navigation_home)
                    true
                }

                R.id.navigation_event -> {
                    // Navigasi ke tujuan "Event"
                    navController.navigate(R.id.navigation_event)
                    true
                }

                else -> false
            }
        }
        navView.itemBackgroundResource = R.drawable.bottomnav_bg

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_account -> {
                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                navController.navigate(R.id.navigation_profile)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}

