package org.hoods.forecastly.common.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import org.hoods.forecastly.utils.Response

inline fun < reified T, reified E> HttpClient.safeRequest(
    crossinline block: HttpRequestBuilder.() -> Unit,
): Flow<Response<T, E>> = flow<Response<T, E>> {
    emit(Response.Loading)
    val response = request { block() }
    emit(Response.Success(response.body<T>()))
}.catch {e ->
    e.printStackTrace()
    val error = when (e) {
        is ClientRequestException -> Response.Error.HttpError(
            e.response.status.value,
            e.errorBody<E>()
        )

        is ServerResponseException -> Response.Error.HttpError(
            e.response.status.value,
            e.errorBody()
        )

        is IOException -> Response.Error.NetworkError
        is SerializationException -> Response.Error.SerializationError
        else -> Response.Error.DefaultError
    }
    emit(error)
}

suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {
        response.body()
    } catch (e: SerializationException) {
        null
    }