package org.hoods.forecastly.geo_location.data.locals

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object GeolocationConstructor:RoomDatabaseConstructor<GeolocationDatabase> {
    override fun initialize(): GeolocationDatabase
}