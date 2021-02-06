package com.mimoupsa.myevents.data.remote.model

data class ClassificationData(
    val family: Boolean,
    val genre: GenreData,
    val primary: Boolean,
    val segment: SegmentData,
    val subGenre: SubGenreData?,
    val subType: SubTypeData?,
    val type: TypeData?
)

data class SegmentData(
    val id: String,
    val name: String
)

data class GenreData(
    val id: String,
    val name: String
)

data class SubGenreData(
    val id: String,
    val name: String
)

data class SubTypeData(
    val id: String,
    val name: String
)

data class TypeData(
    val id: String,
    val name: String
)