package org.hoods.forecastly.geo_location.data.locals

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create():RoomDatabase.Builder<GeolocationDatabase>
}