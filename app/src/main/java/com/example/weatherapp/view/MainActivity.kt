package com.example.weatherapp.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.hw6.MainBroadcastReceiver
import com.example.weatherapp.hw6.ThreadsFragment
import com.example.weatherapp.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val receiver = MainBroadcastReceiver()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance()).commit()

        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(receiver, IntentFilter("myaction"))

        val mySendIntent = Intent("myaction")
        sendBroadcast(mySendIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open_fragment_threads -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ThreadsFragment.newInstance()).commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}