package org.hoods.forecastly.geo_location.data.remote

import kotlinx.coroutines.flow.Flow
import org.hoods.forecastly.geo_location.data.remote.models.GeoLocationDto
import org.hoods.forecastly.utils.ApiErrorResponse
import org.hoods.forecastly.utils.Response

interface GeoLocationRemoteApiService {
    fun searchLocation(query:String):Flow<Response<GeoLocationDto,ApiErrorResponse>>
}