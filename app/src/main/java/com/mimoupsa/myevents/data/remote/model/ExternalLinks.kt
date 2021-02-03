package com.mimoupsa.myevents.data.remote.model

data class ExternalLinks(
    val facebook: List<Facebook>?,
    val homepage: List<Homepage>?,
    val instagram: List<Instagram>?,
    val spotify: List<Spotify>?,
    val twitter: List<Twitter>?,
    val youtube: List<Youtube>?
)
data class Facebook(
        val url: String
)

data class Instagram(
        val url: String
)

data class Homepage(
        val url: String
)

data class Spotify(
        val url: String
)

data class Twitter(
        val url: String
)

data class Youtube(
        val url: String
)
