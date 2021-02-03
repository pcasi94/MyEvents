package com.mimoupsa.myevents.data.remote.model

data class Classification(
    val family: Boolean,
    val genre: Genre,
    val primary: Boolean,
    val segment: Segment,
    val subGenre: SubGenre,
    val subType: SubType,
    val type: Type
)

data class Segment(
    val id: String,
    val name: String
)

data class Genre(
    val id: String,
    val name: String
)

data class SubGenre(
    val id: String,
    val name: String
)

data class SubType(
    val id: String,
    val name: String
)

data class Type(
    val id: String,
    val name: String
)