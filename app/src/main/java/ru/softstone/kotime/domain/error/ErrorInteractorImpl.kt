package ru.softstone.kotime.domain.error

import ru.softstone.kotime.domain.error.model.KotimeError
import javax.inject.Inject

class ErrorInteractorImpl @Inject constructor() : ErrorInteractor {
    private var lastError: KotimeError? = null

    override fun setLastError(description: String, error: Throwable) {
        lastError = KotimeError(description, error)
    }

    override fun getLastError(): KotimeError? {
        return lastError
    }
}