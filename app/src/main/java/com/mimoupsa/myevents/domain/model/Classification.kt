package com.mimoupsa.myevents.domain.model

import androidx.room.Entity

@Entity
data class Classification (
    val tags: List<String?>
    )