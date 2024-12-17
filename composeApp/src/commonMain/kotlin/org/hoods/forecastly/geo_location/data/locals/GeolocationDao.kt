package org.hoods.forecastly.geo_location.data.locals

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.hoods.forecastly.geo_location.data.locals.models.GeoLocationEntity

@Dao
interface GeolocationDao {
    @Upsert
    @Transaction
    suspend fun upsertGeoLocation(geoLocationEntity: GeoLocationEntity)

    @Query("Select * From geolocation_table Limit 1")
    fun getGeoLocation(): Flow<GeoLocationEntity?>

    @Query("Delete From geolocation_table")
    @Transaction
    suspend fun clearGeoLocation()
}