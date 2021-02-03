package com.mimoupsa.myevents.data.remote.model

data class Dates(
    val spanMultipleDays: Boolean,
    val start: Start,
    val status: Status,
    val timezone: String
)

data class Status(
    val code: String
)

data class Start(
    val dateTBA: Boolean,
    val dateTBD: Boolean,
    val dateTime: String,
    val localDate: String,
    val localTime: String,
    val noSpecificTime: Boolean,
    val timeTBA: Boolean
)

