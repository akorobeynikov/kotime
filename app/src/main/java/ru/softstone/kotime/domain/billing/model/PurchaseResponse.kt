package ru.softstone.kotime.domain.billing.model


sealed class PurchaseResponse(val result: Int?) {
    data class PurchaseSuccess(private val billingResponse: Int) : PurchaseResponse(billingResponse)
    data class PurchaseFailure(private val billingResponse: Int? = null) : PurchaseResponse(billingResponse)
}