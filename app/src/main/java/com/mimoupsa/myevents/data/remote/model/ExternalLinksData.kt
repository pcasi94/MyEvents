package com.mimoupsa.myevents.data.remote.model

data class ExternalLinksData(
    val facebook: List<FacebookData>?,
    val homepage: List<HomepageData>?,
    val instagram: List<InstagramData>?,
    val spotify: List<SpotifyData>?,
    val twitter: List<TwitterData>?,
    val youtube: List<YoutubeData>?
)
data class FacebookData(
        val url: String
)

data class InstagramData(
        val url: String
)

data class HomepageData(
        val url: String
)

data class SpotifyData(
        val url: String
)

data class TwitterData(
        val url: String
)

data class YoutubeData(
        val url: String
)
