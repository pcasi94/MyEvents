package com.mimoupsa.myevents.data.remote.model

data class DatesData(
    val spanMultipleDays: Boolean,
    val start: StartData,
    val status: StatusData,
    val timezone: String
)

data class StatusData(
    val code: String
)

data class StartData(
    val dateTBA: Boolean,
    val dateTBD: Boolean,
    val dateTime: String,
    val localDate: String,
    val localTime: String,
    val noSpecificTime: Boolean,
    val timeTBA: Boolean
)

