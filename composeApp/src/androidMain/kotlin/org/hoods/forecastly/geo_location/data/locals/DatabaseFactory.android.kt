package org.hoods.forecastly.geo_location.data.locals

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.hoods.forecastly.geo_location.data.locals.GeolocationDatabase

actual class DatabaseFactory(
    private val context:Context
) {
    actual fun create(): RoomDatabase.Builder<GeolocationDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(GeolocationDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}