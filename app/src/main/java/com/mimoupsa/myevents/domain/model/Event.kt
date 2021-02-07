package com.mimoupsa.myevents.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Event(
        val eventId: String,
        val url: String,
        val name: String,
        val info: String?,
        val emplacement: String?,
        val city: String?,
        val province: String?,
        val country: String?,
        val address: String?,
        val postalCode: String?,
        val location: Location?,
        val date: String?,
        val priceRangesData: List<PriceRanges>?,
        val images: List<Image>?,
        val classifications: List<Classification>?,
        val externalLinks: ExternalLinks?
        )