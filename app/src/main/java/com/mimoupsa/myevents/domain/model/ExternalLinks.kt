package com.mimoupsa.myevents.domain.model

import androidx.room.Entity

@Entity
data class ExternalLinks(
        val facebook: List<Facebook>?,
        val homepage: List<Homepage>?,
        val instagram: List<Instagram>?,
        val spotify: List<Spotify>?,
        val twitter: List<Twitter>?,
        val youtube: List<Youtube>?
)

@Entity
data class Facebook(
        val url: String
)

@Entity
data class Instagram(
        val url: String
)

@Entity
data class Homepage(
        val url: String
)

@Entity
data class Spotify(
        val url: String
)

@Entity
data class Twitter(
        val url: String
)

@Entity
data class Youtube(
        val url: String
)