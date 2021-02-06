package com.mimoupsa.myevents

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mimoupsa.myevents.data.remote.callback.CallbackEvents
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource
import com.mimoupsa.myevents.domain.model.Event

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        EventsApiDataSource.INSTANCE.getEvents(0,object : CallbackEvents{
            override fun onSuccess(data: List<Event>) {
                data.first().eventId
            }

            override fun onError(errorCode: Int) {
                Log.e(MainActivity::class.java.name, "Error: $errorCode")
            }
        })
    }


}