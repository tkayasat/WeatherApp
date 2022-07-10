package com.example.weatherapp.hw6

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"

class MainService(name:String="name"):IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")

        val mySendIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        mySendIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA,"answer ${(0..100).random()}")
        LocalBroadcastManager.getInstance(this).sendBroadcast(mySendIntent)
    }

    override fun onCreate() {
        createLogMessage("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        createLogMessage("onDestroy")
        super.onDestroy()
    }

    private fun createLogMessage(message: String) {
        //createLogMessage("createLogMessage")
        Log.d("mylogs", message)
    }

}