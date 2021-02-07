package com.mimoupsa.myevents.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mimoupsa.myevents.domain.model.Location

@Entity
data class EventPOJO (
    @PrimaryKey
    val eventId: String,
    val url: String,
    val name: String,
    val emplacement: String,
    val city: String,
    val province: String,
    val date: String,
    val image: String
    )