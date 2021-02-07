package com.mimoupsa.myevents.domain.model

import androidx.room.Entity

@Entity
data class Location(
        val longitude: String?,
        val latitude: String?
)