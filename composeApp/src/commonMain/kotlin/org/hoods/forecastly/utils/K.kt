package org.hoods.forecastly.utils

object K {
    fun flagUrl(countryCode:String): FlagUrl = "https://flagsapi.com/$countryCode/flat/64.png"
    const val FORECAST_BASE_URL = "https://api.open-meteo.com/v1/"
    const val GEO_CODING_BASE_URL = "https://geocoding-api.open-meteo.com/v1/"
    const val FORECAST_END_POINT = "forecast"
    const val GEO_CODING_END_POINT = "search"
}

object ApiParameters{
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val DAILY = "daily"
    const val CURRENT_WEATHER = "current"
    const val HOURLY = "hourly"
    const val TIME_FORMAT = "timeformat"
    const val TIMEZONE = "timezone"
    const val GeoName = "name"
}

typealias FlagUrl = String