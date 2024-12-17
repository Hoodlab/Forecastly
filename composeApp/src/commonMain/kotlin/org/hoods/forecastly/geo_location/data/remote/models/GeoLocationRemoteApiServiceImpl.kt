package org.hoods.forecastly.geo_location.data.remote.models

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import org.hoods.forecastly.common.data.safeRequest
import org.hoods.forecastly.geo_location.data.remote.GeoLocationRemoteApiService
import org.hoods.forecastly.utils.ApiErrorResponse
import org.hoods.forecastly.utils.K
import org.hoods.forecastly.utils.Response

class GeoLocationRemoteApiServiceImpl(
    private val httpClient: HttpClient
):GeoLocationRemoteApiService {
    override fun searchLocation(query: String): Flow<Response<GeoLocationDto, ApiErrorResponse>> {
        return httpClient.safeRequest<GeoLocationDto,ApiErrorResponse> {
            url(urlString = K.GEO_CODING_BASE_URL + "/${K.GEO_CODING_END_POINT}")
            parameter("name",query)
        }
    }
}