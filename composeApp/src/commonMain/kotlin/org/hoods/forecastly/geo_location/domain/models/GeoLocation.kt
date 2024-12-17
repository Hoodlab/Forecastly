package org.hoods.forecastly.geo_location.domain.models

import org.hoods.forecastly.utils.FlagUrl

data class GeoLocation(
    val id:Int = 0,
    val name:String,
    val countryName:String,
    val countryCode:String,
    val flagUrl: FlagUrl,
    val countryId:Int,
    val latitude:Double,
    val longitude:Double,
    val timeZone:String,
    val elevation:Double,
)
