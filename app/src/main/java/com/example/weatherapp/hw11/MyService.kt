package com.example.weatherapp.hw11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R
import com.example.weatherapp.view.main.MainActivityWebView
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val remoteMessageData = remoteMessage.data
        if (remoteMessageData.isNotEmpty()) {
            val title = remoteMessageData[PUSH_KEY_TITLE]
            val message = remoteMessageData[PUSH_KEY_MESSAGE]
            if(!title.isNullOrBlank()&&!message.isNullOrBlank()){
                pushNotification(title, message)
            }
        }
    }

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID_1 = "channel_id_1"
    }


    private fun pushNotification(title: String, message: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val intentAction = Intent(this, MainActivityWebView::class.java)
        intentAction.putExtra("action", "actionName")
        val intent = PendingIntent.getActivity(
            this, 0,
            intentAction, 0
        )
        val notificationBuilder_1 = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setSmallIcon(R.drawable.ic_map_pin)
            setContentTitle(title)
            setContentText(message)
            addAction(R.drawable.ic_map_maker, "Open app", intent)
            priority = NotificationCompat.PRIORITY_MAX
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nameChannel_1 = "Name ${CHANNEL_ID_1}"
            val descChannel_1 = "Description ${CHANNEL_ID_1}"
            val importanceChannel_1 = NotificationManager.IMPORTANCE_HIGH
            val channel_1 =
                NotificationChannel(CHANNEL_ID_1, nameChannel_1, importanceChannel_1).apply {
                    description = descChannel_1
                }
            notificationManager.createNotificationChannel(channel_1)
        }
        notificationManager.notify((0..10000).random(), notificationBuilder_1.build())
    }
}