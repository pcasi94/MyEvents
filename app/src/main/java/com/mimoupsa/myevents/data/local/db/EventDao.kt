package com.mimoupsa.myevents.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDao {

    @Query("SELECT * FROM EventPOJO")
    fun getAll(): LiveData<List<EventPOJO>>

    @Query("SELECT * FROM EventPOJO WHERE eventId = :id")
    suspend fun getById(id: String): EventPOJO?

    @Update
    suspend fun update(event: EventPOJO)

    @Insert
    suspend fun insert(event: EventPOJO)

    @Delete
    suspend fun delete(event: EventPOJO)

}