package org.hoods.forecastly.forecast.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.hoods.forecastly.forecast.data.mapper.ApiWeatherMapper
import org.hoods.forecastly.forecast.data.remote.ForecastRemoteApiService
import org.hoods.forecastly.forecast.domain.models.Weather
import org.hoods.forecastly.forecast.domain.repository.ForecastRepository
import org.hoods.forecastly.utils.ApiErrorResponse
import org.hoods.forecastly.utils.Response
import org.hoods.forecastly.utils.map

class ForecastRepositoryImpl(
    private val forecastRemoteApiService: ForecastRemoteApiService,
    private val mapper:ApiWeatherMapper,
    private val externalScope:CoroutineScope
):ForecastRepository {
    private val _weatherData = MutableStateFlow<Response<Weather,ApiErrorResponse>?>(null)
    override val weatherData: StateFlow<Response<Weather, ApiErrorResponse>?>
        get() = _weatherData.asStateFlow()

    override fun fetchWeatherData(latitude: Float, longitude: Float, timeZone: String) {
        forecastRemoteApiService.fetchForecast(
            latitude = latitude, longitude = longitude, timeZone = timeZone
        ).map { response ->response.map { mapper.mapToDomain(it, timeZone = timeZone) } }
            .onEach { result ->
                _weatherData.update {
                    result
                }
            }.launchIn(externalScope)
    }
}