package org.hoods.forecastly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform