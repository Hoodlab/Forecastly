package org.hoods.forecastly.forecast.data.mapper

import org.hoods.forecastly.forecast.data.remote.models.WeatherDto
import org.hoods.forecastly.forecast.domain.models.Weather

class ApiWeatherMapper(
    private val apiDailyWeatherMapper: ApiDailyWeatherMapper,
    private val currentWeatherMapper: CurrentWeatherMapper,
    private val apiHourlyMapper: ApiHourlyMapper,
):ApiMapper<Weather,WeatherDto> {
    override fun mapToDomain(model: WeatherDto, timeZone: String): Weather {
        return Weather(
            currentWeather = currentWeatherMapper.mapToDomain(model.current,timeZone),
            daily = apiDailyWeatherMapper.mapToDomain(model.daily,timeZone),
            hourly = apiHourlyMapper.mapToDomain(model.hourly,timeZone),
            timezone = timeZone
        )
    }
}