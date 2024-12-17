package org.hoods.forecastly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.geo_location.domain.repository.GeoLocationRepository
import org.hoods.forecastly.utils.Response

class HomeViewModel(
    private val geoLocationRepository: GeoLocationRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        getGeoLocation()
    }


    fun getGeoLocation() = viewModelScope.launch {
        geoLocationRepository.geoLocation
            .collect {
                _homeState.update { state ->
                    state.copy(
                        selectedLocation = it,
                        isLocationSelected = it != null,
                    )
                }
            }

    }

    fun saveFavouriteLocation() = viewModelScope.launch {
        homeState.value.selectedLocation?.let {
            geoLocationRepository.upsertLocation(it)
        }
    }

    fun setSelectedLocation(geoLocation: GeoLocation) {
        _homeState.update {
            it.copy(
                selectedLocation = geoLocation.copy(id = 1),
                isLocationSelected = true,
            )
        }
    }

    fun fetchGeoLocation(query: String) {
        viewModelScope.launch {
            geoLocationRepository.fetchGeoLocation(query).collect { result ->
                when (result) {
                    is Response.Success -> {
                        _homeState.update {
                            it.copy(isLoading = false, error = null, geoLocations = result.data)
                        }
                    }

                    is Response.Error.DefaultError -> {
                        _homeState.update {
                            it.copy(isLoading = false, error = "Error Occurred")
                        }
                    }

                    is Response.Error.NetworkError -> {
                        _homeState.update {
                            it.copy(isLoading = false, error = "No Network")
                        }
                    }

                    is Response.Error.SerializationError -> {
                        _homeState.update {
                            it.copy(isLoading = false, error = "Failed to Serialize Data")
                        }
                    }

                    is Response.Error.HttpError -> {
                        _homeState.update {
                            it.copy(isLoading = false, error = result.code.toString())
                        }
                    }

                    is Response.Loading -> {
                        _homeState.update {
                            it.copy(isLoading = true, error = null)
                        }
                    }
                }
            }
        }
    }


}

data class HomeState(
    val isLocationSelected: Boolean = false,
    val selectedLocation: GeoLocation? = null,
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val geoLocations: List<GeoLocation> = emptyList()
)