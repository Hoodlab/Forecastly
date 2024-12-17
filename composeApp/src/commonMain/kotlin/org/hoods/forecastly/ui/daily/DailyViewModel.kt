package org.hoods.forecastly.ui.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hoods.forecastly.forecast.data.repository.ForecastRepositoryImpl
import org.hoods.forecastly.forecast.domain.models.Daily
import org.hoods.forecastly.forecast.domain.repository.ForecastRepository
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.geo_location.domain.repository.GeoLocationRepository
import org.hoods.forecastly.utils.Response

class DailyViewModel (
    private val repository: ForecastRepository,
    private val geoLocationRepository: GeoLocationRepository
):ViewModel(){
    private var _dailyState = MutableStateFlow(DailyState())
    val dailyState = _dailyState.asStateFlow()

    init {
        observeWeatherData()
    }

    private fun observeWeatherData() = viewModelScope.launch {
        repository.weatherData.collect { response ->
            when (response) {
                is Response.Loading -> {
                    _dailyState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }

                is Response.Success -> {
                    _dailyState.update {
                        it.copy(
                            isLoading = false,
                            daily = response.data.daily,
                            error = null
                        )
                    }
                }

                is Response.Error.DefaultError -> {
                    _dailyState.update {
                        it.copy(isLoading = false, error = "Error Occurred")
                    }
                }

                is Response.Error.NetworkError -> {
                    _dailyState.update {
                        it.copy(isLoading = false, error = "No Network")
                    }
                }

                is Response.Error.SerializationError -> {
                    _dailyState.update {
                        it.copy(isLoading = false, error = "Failed to Serialize Data")
                    }
                }

                is Response.Error.HttpError -> {
                    _dailyState.update {
                        it.copy(isLoading = false, error = response.code.toString())
                    }
                }
                else -> {}
            }
        }
    }


}

data class DailyState(
    val daily: Daily? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val geoLocation: GeoLocation? = null
)