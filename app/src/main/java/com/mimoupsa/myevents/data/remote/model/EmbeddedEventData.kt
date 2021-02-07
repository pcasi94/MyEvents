package com.mimoupsa.myevents.data.remote.model

data class EmbeddedEvent (
    val venues: List<VenueData>,
    val attractions: List<AttractionData>
)

data class VenueData(
        val name: String,
        val type: String,
        val id: String,
        val test: Boolean,
        val url: String,
        val locale: String,
        val postalCode: String,
        val timeZone: String,
        val city: CityData,
        val state: StateData,
        val country: CountryData,
        val address: AddressData,
        val location: LocationData,
)

data class AttractionData(
        val classifications: List<ClassificationData>,
        val externalLinks: ExternalLinksData?,
        val id: String,
        val images: List<ImageData>,
        val locale: String,
        val name: String,
        val test: Boolean,
        val type: String,
        val url: String
)

data class CityData(
        val name: String
)

data class  StateData(
        val name: String
)

data class CountryData(
        val name: String,
        val countryCode: String
)

data class AddressData(
        val line1: String
)

data class LocationData(
        val longitude: String,
        val latitude: String
)


