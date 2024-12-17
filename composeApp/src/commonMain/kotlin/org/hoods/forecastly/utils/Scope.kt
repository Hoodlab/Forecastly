package org.hoods.forecastly.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun provideExternalCoroutineScope(dispatcher: CoroutineDispatcher = Dispatchers.Default): CoroutineScope {
    return CoroutineScope(SupervisorJob() + dispatcher)
}