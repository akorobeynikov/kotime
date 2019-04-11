package ru.softstone.kotime.domain.error

import ru.softstone.kotime.domain.error.model.KotimeError

interface ErrorInteractor {
    fun setLastError(description: String, error: Throwable)
    fun getLastError(): KotimeError?
}