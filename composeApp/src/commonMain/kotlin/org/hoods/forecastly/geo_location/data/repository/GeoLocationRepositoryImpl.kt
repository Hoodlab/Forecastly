package org.hoods.forecastly.geo_location.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import org.hoods.forecastly.common.data.Mapper
import org.hoods.forecastly.geo_location.data.locals.GeolocationDao
import org.hoods.forecastly.geo_location.data.locals.models.GeoLocationEntity
import org.hoods.forecastly.geo_location.data.remote.GeoLocationRemoteApiService
import org.hoods.forecastly.geo_location.data.remote.models.toDomain
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.geo_location.domain.repository.GeoLocationRepository
import org.hoods.forecastly.utils.ApiErrorResponse
import org.hoods.forecastly.utils.Response
import org.hoods.forecastly.utils.map

class GeoLocationRepositoryImpl(
    private val geoLocationRemoteApiService: GeoLocationRemoteApiService,
    private val geolocationDao: GeolocationDao,
    private val geolocationMapper: Mapper<GeoLocation,GeoLocationEntity>,
    private val externalScope:CoroutineScope
):GeoLocationRepository {
    override val geoLocation: Flow<GeoLocation?>
        get() {
            return geolocationDao.getGeoLocation().map {
                geolocationMapper.mapToDomainOrNull(it)
            }.shareIn(scope = externalScope, started = SharingStarted.Lazily)
        }

    override suspend fun upsertLocation(geoLocation: GeoLocation) {
        geolocationDao.upsertGeoLocation(geolocationMapper.mapFromDomain(geoLocation))
    }

    override fun fetchGeoLocation(query: String): Flow<Response<List<GeoLocation>, ApiErrorResponse>> {
        return geoLocationRemoteApiService.searchLocation(query).map { response ->
            response.map { geoLocationDto ->
                geoLocationDto.toDomain()
            }
        }
    }

    override suspend fun clearGeoLocation() {
        geolocationDao.clearGeoLocation()
    }

    override suspend fun clear() {
        externalScope.cancel()
    }
}