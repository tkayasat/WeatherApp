package com.example.weatherapp

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherapp.room.HistoryDAO
import com.example.weatherapp.room.HistoryDataBase
import java.lang.IllegalStateException

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance:MyApp? = null
        private var db:HistoryDataBase? = null
        private const val DB_NAME = "HistoryDataBase.db"

        fun getHistoryDAO():HistoryDAO{
            if(db==null){
                if(appInstance!=null){
                    db = Room.databaseBuilder(appInstance!!.applicationContext,HistoryDataBase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }else{
                    throw  IllegalStateException("appInstance==null")
                }
            }
            return db!!.historyDAO()
        }
    }
}