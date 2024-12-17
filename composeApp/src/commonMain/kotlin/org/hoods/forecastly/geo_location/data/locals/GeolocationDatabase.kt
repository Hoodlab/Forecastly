package org.hoods.forecastly.geo_location.data.locals

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import org.hoods.forecastly.geo_location.data.locals.models.GeoLocationEntity

@Database(entities = [GeoLocationEntity::class], version = 1)
@ConstructedBy(GeolocationConstructor::class)
abstract class GeolocationDatabase:RoomDatabase() {
    companion object{
        const val DB_NAME = "geo_location.db"
    }

    abstract fun geolocationDao(): GeolocationDao

}