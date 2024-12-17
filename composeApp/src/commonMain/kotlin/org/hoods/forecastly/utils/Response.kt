package org.hoods.forecastly.utils

sealed class Response<out T, out E> {
    data class Success<out T>(val data: T) : Response<T, Nothing>()
    sealed class Error<E> : Response<Nothing, E>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError<E>(val code: Int, val errorBody: E?) : Error<E>()

        /**
         * Represent IOExceptions and connectivity issues.
         */
        data object NetworkError : Error<Nothing>()

        /**
         * Represent SerializationExceptions.
         */
        data object SerializationError : Error<Nothing>()

        data object DefaultError : Error<Nothing>()
    }
    data object Loading : Response<Nothing, Nothing>()
}

inline fun <T, E, R> Response<T, E>.map(mapper: (T) -> R): Response<R, E> = when (this) {
    is Response.Success -> Response.Success(mapper(data))
    is Response.Error -> this // Propagate Error as is
    is Response.Loading -> this // Propagate Loading as is
}