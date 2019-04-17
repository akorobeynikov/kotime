package ru.softstone.kotime.domain.billing.model

import com.android.billingclient.api.SkuDetails

sealed class ItemsForPurchaseResponse(
    val result: Int?,
    val skus: List<SkuDetails>? = null
) {

    data class ItemsForPurchaseSuccess(
        private val billingResponse: Int,
        private val items: List<SkuDetails>
    ) : ItemsForPurchaseResponse(billingResponse, items)

    data class ItemsForPurchaseFailure(
        private val billingResponse: Int? = null
    ) : ItemsForPurchaseResponse(billingResponse)

}