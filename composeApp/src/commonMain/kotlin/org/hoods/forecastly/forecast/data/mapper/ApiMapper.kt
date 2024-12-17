package org.hoods.forecastly.forecast.data.mapper

interface ApiMapper<Domain,Model> {
    fun mapToDomain(model: Model,timeZone:String = ""):Domain
}