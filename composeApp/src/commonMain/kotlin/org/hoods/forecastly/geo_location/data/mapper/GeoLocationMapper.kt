package org.hoods.forecastly.geo_location.data.mapper

import org.hoods.forecastly.common.data.Mapper
import org.hoods.forecastly.geo_location.data.locals.models.GeoLocationEntity
import org.hoods.forecastly.geo_location.domain.models.GeoLocation
import org.hoods.forecastly.utils.K

class GeoLocationMapper: Mapper<GeoLocation, GeoLocationEntity> {
    override fun mapToDomainOrNull(model: GeoLocationEntity?): GeoLocation? {
        return model?.run {
            GeoLocation(
                id = id,
                name = name,
                countryName = countryName,
                countryCode = countryCode,
                countryId = countryId,
                latitude = latitude,
                longitude = longitude,
                timeZone = timeZone,
                elevation = elevation,
                flagUrl = K.flagUrl(countryCode)
            )
        }
    }

    override fun mapFromDomain(domain: GeoLocation): GeoLocationEntity {
        return domain.run {
            GeoLocationEntity(
                id = id,
                name = name,
                countryName = countryName,
                countryCode = countryCode,
                countryId = countryId,
                latitude = latitude,
                longitude = longitude,
                timeZone = timeZone,
                elevation = elevation
            )
        }
    }
}