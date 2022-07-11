package com.example.weatherapp.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R
import com.example.weatherapp.history.HistoryFragment
import com.example.weatherapp.view.main.MainFragment
import com.google.android.gms.maps.MapFragment.newInstance
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {


    companion object {
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val CHANNEL_ID_1 = "channel_id_1"
        private const val CHANNEL_ID_2 = "channel_id_2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance()).commit()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { it ->
            if (it.isSuccessful) {
                Log.d("mylogs", it.result.toString())
            }
        }
    }

    private fun pushNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder_1 = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setSmallIcon(R.drawable.ic_map_pin)
            setContentTitle("Заголовок для $CHANNEL_ID_1")
            setContentText("Сообщение для $CHANNEL_ID_1")
            priority = NotificationCompat.PRIORITY_MAX
        }
        val notificationBuilder_2 = NotificationCompat.Builder(this, CHANNEL_ID_2).apply {
            setSmallIcon(R.drawable.ic_map_pin)
            setContentTitle("Заголовок для $CHANNEL_ID_2")
            setContentText("Сообщение для $CHANNEL_ID_2")
            priority = NotificationCompat.PRIORITY_MAX
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nameChannel_2 = "Name $CHANNEL_ID_2"
            val descChannel_2 = "Description $CHANNEL_ID_2"
            val importanceChannel_2 = NotificationManager.IMPORTANCE_HIGH
            val channel_2 =
                NotificationChannel(CHANNEL_ID_2, nameChannel_2, importanceChannel_2).apply {
                    description = descChannel_2
                }
            notificationManager.createNotificationChannel(channel_2)
        }
        notificationManager.notify(NOTIFICATION_ID_2, notificationBuilder_2.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nameChannel_1 = "Name $CHANNEL_ID_1"
            val descChannel_1 = "Description $CHANNEL_ID_1"
            val importanceChannel_1 = NotificationManager.IMPORTANCE_MIN
            val channel_1 =
                NotificationChannel(CHANNEL_ID_1, nameChannel_1, importanceChannel_1).apply {
                    description = descChannel_1
                }
            notificationManager.createNotificationChannel(channel_1)
        }
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilder_1.build())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open_fragment_history -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HistoryFragment.newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            R.id.action_open_map -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}