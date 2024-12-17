package org.hoods.forecastly.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hoods.forecastly.forecast.domain.models.Daily
import org.hoods.forecastly.forecast.domain.models.Weather
import org.hoods.forecastly.forecast.domain.repository.ForecastRepository
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.geo_location.domain.repository.GeoLocationRepository
import org.hoods.forecastly.utils.Response
import org.hoods.forecastly.utils.Util

class ForecastViewModel(
    private val repository: ForecastRepository,
    private val geoLocationRepository: GeoLocationRepository
):ViewModel() {
    private val _forecast = MutableStateFlow(ForecastState())
    val forecastState = _forecast.asStateFlow()

    init {
        getGeolocation()
    }
    init {
        observerWeatherData()
    }

    private fun observerWeatherData() = viewModelScope.launch {
        repository.weatherData.collect{response ->
            when (response) {
                is Response.Loading -> {
                    _forecast.update {
                        it.copy(isLoading = true, error = null)
                    }
                }

                is Response.Success -> {
                    _forecast.update {
                        it.copy(isLoading = false, error = null, weather = response.data)
                    }
                    val todayDailWeatherInfo = response.data.daily.weatherInfo.find {
                        Util.isTodayDate(it.time)
                    }
                    _forecast.update {
                        it.copy(
                            dailyWeatherInfo = todayDailWeatherInfo
                        )
                    }
                }

                is Response.Error.DefaultError -> {
                    _forecast.update {
                        it.copy(isLoading = false, error = "Error Occurred")
                    }
                }

                is Response.Error.NetworkError -> {
                    _forecast.update {
                        it.copy(isLoading = false, error = "No Network")
                    }
                }

                is Response.Error.SerializationError -> {
                    _forecast.update {
                        it.copy(isLoading = false, error = "Failed to Serialize Data")
                    }
                }

                is Response.Error.HttpError -> {
                    _forecast.update {
                        it.copy(isLoading = false, error = response.code.toString())
                    }
                }

                else -> {}
            }
        }
    }

    fun getGeolocation() = viewModelScope.launch {
        geoLocationRepository.geoLocation.collect{
            _forecast.update { state ->
                state.copy(selectedLocation = it)
            }
        }
    }

    fun fetchWeatherData(){
        viewModelScope.launch {
            forecastState.value.selectedLocation?.let { geolocation ->
                repository.fetchWeatherData(
                    latitude = geolocation.latitude.toFloat(),
                    longitude = geolocation.longitude.toFloat(),
                    timeZone = geolocation.timeZone
                )
            }
        }
    }

}

data class ForecastState(
    val weather: Weather? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val dailyWeatherInfo: Daily.WeatherInfo? = null,
    val selectedLocation: GeoLocation? = null,
)