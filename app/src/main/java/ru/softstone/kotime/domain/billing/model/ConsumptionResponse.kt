package ru.softstone.kotime.domain.billing.model

sealed class ConsumptionResponse(
    val result: Int?,
    val outToken: String?
) {

    data class ConsumptionSuccess(
        private val billingResponse: Int,
        private val outputToken: String
    ) : ConsumptionResponse(billingResponse, outToken = outputToken)

    data class ConsumptionFailure(
        private val billingResponse: Int? = null
    ) : ConsumptionResponse(billingResponse, outToken = null)

}