package com.mimoupsa.myevents.domain.mappers

import android.media.FaceDetector
import com.mimoupsa.myevents.data.remote.model.*
import com.mimoupsa.myevents.domain.model.*

object EventMapper: Mapper<EventData, Event>(){
    override fun map(unmapped: EventData): Event {
        return Event(
                eventId = unmapped.id,
                url = unmapped.url,
                name = unmapped.name,
                info = unmapped.info,
                emplacement = unmapped.embedded.venues.first().name,
                city = unmapped.embedded.venues.first().city.name,
                province = unmapped.embedded.venues.first().state.name,
                country = unmapped.embedded.venues.first().country.name,
                address = unmapped.embedded.venues.first().address.line1,
                postalCode = unmapped.embedded.venues.first().postalCode,
                location = LocationMapper.map(unmapped.embedded.venues.first().location),
                date = unmapped.dates.start.dateTime,
                priceRangesData = unmapped.priceRanges?.let { PriceRangesMapper.map(it) },
                images = unmapped.images.let { ImageMapper.map(it) },
                classifications = unmapped.classifications.let { ClassificationsMapper.map(it) },
                externalLinks = unmapped.embedded.attractions.first().externalLinks.let { ExternalLinksMapper.map(it) }
        )
    }
}


object LocationMapper: Mapper<LocationData,Location>(){
    override fun map(unmapped: LocationData): Location {
        return Location(
                latitude = unmapped.latitude,
                longitude = unmapped.longitude
        )
    }
}

object PriceRangesMapper: Mapper<PriceRangesData,PriceRanges>(){
    override fun map(unmapped: PriceRangesData): PriceRanges {
        return PriceRanges(
                currency = unmapped.currency,
                max = unmapped.max,
                min = unmapped.min,
                type = unmapped.type
        )
    }
}

object ImageMapper: Mapper<ImageData,Image>(){
    override fun map(unmapped: ImageData): Image {
        return Image(
                url = unmapped.url
        )
    }
}

object ClassificationsMapper: Mapper<ClassificationData, Classification>(){
    override fun map(unmapped: ClassificationData): Classification {
        return Classification(
                tags = listOf(unmapped.segment.name, unmapped.genre.name, unmapped.type?.name, unmapped.subGenre?.name, unmapped.subType?.name)
        )
    }
}

object ExternalLinksMapper: Mapper<ExternalLinksData,ExternalLinks>(){
    override fun map(unmapped: ExternalLinksData): ExternalLinks {
        return ExternalLinks(
                facebook = unmapped.facebook?.let { FacebookMapper.map(it) },
                instagram = unmapped.instagram?.let { InstagramMapper.map(it) },
                homepage = unmapped.homepage?.let { HomepageMapper.map(it) },
                spotify = unmapped.spotify?.let { SpotifyMapper.map(it) },
                twitter = unmapped.twitter?.let { TwitterMapper.map(it) },
                youtube = unmapped.youtube?.let { YoutubeMapper.map(it) }
        )
    }
}

object FacebookMapper: Mapper<FacebookData,Facebook>(){
    override fun map(unmapped: FacebookData): Facebook {
        return Facebook(
                url = unmapped.url
        )
    }
}

object InstagramMapper: Mapper<InstagramData, Instagram>(){
    override fun map(unmapped: InstagramData): Instagram {
        return Instagram(
                url = unmapped.url
        )
    }
}

object HomepageMapper: Mapper<HomepageData, Homepage>(){
    override fun map(unmapped: HomepageData): Homepage {
        return Homepage(
                url = unmapped.url
        )
    }
}

object SpotifyMapper: Mapper<SpotifyData, Spotify>(){
    override fun map(unmapped: SpotifyData): Spotify {
        return Spotify(
                url = unmapped.url
        )
    }
}

object TwitterMapper: Mapper<TwitterData, Twitter>(){
    override fun map(unmapped: TwitterData): Twitter {
        return Twitter(
                url = unmapped.url
        )
    }
}

object YoutubeMapper: Mapper<YoutubeData,Youtube>(){
    override fun map(unmapped: YoutubeData): Youtube {
        return Youtube(
                url = unmapped.url
        )
    }
}
