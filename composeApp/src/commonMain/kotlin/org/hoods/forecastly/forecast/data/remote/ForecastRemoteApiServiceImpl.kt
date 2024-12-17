package org.hoods.forecastly.forecast.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import org.hoods.forecastly.common.data.safeRequest
import org.hoods.forecastly.forecast.data.remote.models.WeatherDto
import org.hoods.forecastly.utils.ApiErrorResponse
import org.hoods.forecastly.utils.ApiParameters
import org.hoods.forecastly.utils.K
import org.hoods.forecastly.utils.Response

class ForecastRemoteApiServiceImpl(
    private val httpClient: HttpClient
):ForecastRemoteApiService {
    override fun fetchForecast(
        latitude: Float,
        longitude: Float,
        daily: Array<String>,
        currentWeather: Array<String>,
        hourlyWeather: Array<String>,
        timeformat: String,
        timeZone: String
    ): Flow<Response<WeatherDto, ApiErrorResponse>> {
        return httpClient.safeRequest {
            url(urlString = "${K.FORECAST_BASE_URL}/${K.FORECAST_END_POINT}")
            parameter(ApiParameters.LATITUDE, latitude.toString())
            parameter(ApiParameters.LONGITUDE, longitude.toString())
            parameter(ApiParameters.DAILY, daily.joinToString(","))
            parameter(ApiParameters.CURRENT_WEATHER, currentWeather.joinToString(","))
            parameter(ApiParameters.HOURLY, hourlyWeather.joinToString(","))
            parameter(ApiParameters.TIME_FORMAT, timeformat)
            parameter(ApiParameters.TIMEZONE, timeZone)
        }
    }
}