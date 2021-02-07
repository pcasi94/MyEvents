package com.mimoupsa.myevents.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EventPOJO::class],
    version = 1
)

abstract class EventDB : RoomDatabase(){

    abstract fun eventDao(): EventDao
}