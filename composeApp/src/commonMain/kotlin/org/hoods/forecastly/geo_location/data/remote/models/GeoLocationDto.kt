package org.hoods.forecastly.geo_location.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.utils.K

@Serializable
data class GeoLocationDto(
    @SerialName("generationtime_ms")
    val generationtimeMs: Double = 0.0,
    @SerialName("results")
    val results: List<Result> = listOf()
)

fun GeoLocationDto.toDomain():List<GeoLocation>{
    return results.map {
        GeoLocation(
            id = it.id,
            name = it.name,
            countryCode = it.countryCode,
            flagUrl = K.flagUrl(it.countryCode),
            countryId = it.countryId,
            latitude = it.latitude,
            longitude = it.longitude,
            timeZone = it.timezone,
            elevation = it.elevation.toDouble(),
            countryName = it.country
        )
    }

}






