package org.hoods.forecastly.geo_location.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("admin1")
    val admin1: String = "",
    @SerialName("admin1_id")
    val admin1Id: Int = 0,
    @SerialName("admin2")
    val admin2: String = "",
    @SerialName("admin2_id")
    val admin2Id: Int = 0,
    @SerialName("admin3")
    val admin3: String = "",
    @SerialName("admin3_id")
    val admin3Id: Int = 0,
    @SerialName("admin4")
    val admin4: String = "",
    @SerialName("admin4_id")
    val admin4Id: Int = 0,
    @SerialName("country")
    val country: String = "",
    @SerialName("country_code")
    val countryCode: String = "",
    @SerialName("country_id")
    val countryId: Int = 0,
    @SerialName("elevation")
    val elevation: Double = 0.0,
    @SerialName("feature_code")
    val featureCode: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("latitude")
    val latitude: Double = 0.0,
    @SerialName("longitude")
    val longitude: Double = 0.0,
    @SerialName("name")
    val name: String = "",
    @SerialName("population")
    val population: Int = 0,
    @SerialName("postcodes")
    val postcodes: List<String> = listOf(),
    @SerialName("timezone")
    val timezone: String = ""
)