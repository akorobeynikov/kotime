package ru.softstone.kotime.domain.billing.model

sealed class ConnectionResult(val result: Int?) {
    data class ConnectionSuccess(private val billingResponse: Int) : ConnectionResult(billingResponse)
    data class ConnectionFailure(private val billingResponse: Int? = null) : ConnectionResult(billingResponse)
}