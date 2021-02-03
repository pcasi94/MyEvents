package com.mimoupsa.myevents.data.remote.model

data class EmbeddedEvent (
    val venues: List<Venue>,
    val attractions: List<Attraction>
)

data class Venue(
        val name: String,
        val type: String,
        val id: String,
        val test: Boolean,
        val url: String,
        val locale: String,
        val postalCode: String,
        val timeZone: String,
        val city: City,
        val state: State,
        val country: Country,
        val address: Address,
        val location: Location,
)

data class Attraction(
        val classifications: List<Classification>,
        val externalLinks: ExternalLinks,
        val id: String,
        val images: List<Image>,
        val locale: String,
        val name: String,
        val test: Boolean,
        val type: String,
        val url: String
)

data class City(
        val name: String
)

data class  State(
        val name: String
)

data class Country(
        val name: String,
        val countryCode: String
)

data class Address(
        val line1: String
)

data class Location(
        val longitude: String,
        val latitude: String
)


