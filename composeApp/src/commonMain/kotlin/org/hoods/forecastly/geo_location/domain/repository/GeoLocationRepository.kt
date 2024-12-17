package org.hoods.forecastly.geo_location.domain.repository

import kotlinx.coroutines.flow.Flow
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.utils.ApiErrorResponse
import org.hoods.forecastly.utils.Response

interface GeoLocationRepository {
    val geoLocation: Flow<GeoLocation?>
    suspend fun upsertLocation(geoLocation: GeoLocation)
    fun fetchGeoLocation(query:String): Flow<Response<List<GeoLocation>, ApiErrorResponse>>
    suspend fun clearGeoLocation()
    suspend fun clear()
}