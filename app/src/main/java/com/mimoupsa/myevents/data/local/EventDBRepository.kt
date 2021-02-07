package com.mimoupsa.myevents.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.mimoupsa.myevents.MyEventsApp
import com.mimoupsa.myevents.data.local.db.EventPOJO
import com.mimoupsa.myevents.data.remote.datasource.EventsApiDataSource

class EventDBRepository(application: Application) {
    val app = MyEventsApp(application.applicationContext)

    fun getAllEvents(): LiveData<List<EventPOJO>>{
        return app.room.eventDao().getAll()
    }

    suspend fun insertEvent(event: EventPOJO): Boolean{
        app.room.eventDao().getById(event.eventId)?.let {
            return false
        }
        app.room.eventDao().insert(event)
        return true
    }

    suspend fun deleteEvent(event: EventPOJO): Boolean{
        app.room.eventDao().delete(event)
        return true
    }
}