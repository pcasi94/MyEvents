package com.mimoupsa.myevents

import android.content.Context
import androidx.room.Room
import com.mimoupsa.myevents.data.local.db.EventDB

class MyEventsApp(context: Context){
    val room = Room
        .databaseBuilder(context, EventDB::class.java,"event")
        .build()
}