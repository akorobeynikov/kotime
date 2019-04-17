package ru.softstone.kotime.domain.billing

import android.app.Activity
import com.android.billingclient.api.Purchase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.softstone.kotime.domain.billing.model.*
import java.lang.ref.WeakReference
import javax.inject.Inject

class BillingInteractorImpl @Inject constructor(
    private val billingSource: BillingSource
) : BillingInteractor {
    companion object {
        private const val SMALL_DONATE_SKU = "kotime_bronze_contribution"
        private const val MEDIUM_DONATE_SKU = "kotime_silver_contribution"
        private const val LARGE_DONATE_SKU = "kotime_gold_contribution"
        private const val TEST_SKU = "android.test.purchased"
    }

    override fun getItemsForPurchase(): Observable<List<ItemForPurchase>> {
        return billingSource.queryItemsForPurchase(listOf(LARGE_DONATE_SKU, MEDIUM_DONATE_SKU, SMALL_DONATE_SKU)).map {
            if (it is ItemsForPurchaseResponse.ItemsForPurchaseSuccess) {
                return@map it.skus
                    ?.sortedWith(Comparator { a, b ->
                        when {
                            a.priceAmountMicros > b.priceAmountMicros -> 1
                            a.priceAmountMicros < b.priceAmountMicros -> -1
                            else -> 0
                        }
                    })
                    ?.map { skuDetails -> ItemForPurchase(skuDetails.sku, skuDetails.price) }
                    ?: emptyList()
            } else {
                return@map emptyList<ItemForPurchase>()
            }
        }
    }

    override fun purchaseItem(skuId: String, activity: WeakReference<Activity>): Observable<PurchaseResponse> {
        return billingSource.purchaseItem(skuId, activity)
    }

    override fun queryPurchases(): Single<List<Purchase>> {
        return billingSource.queryPurchases()
    }

    override fun consumeItem(purchaseToken: String): Observable<ConsumptionResponse> {
        return billingSource.consumeItem(purchaseToken)
    }

    override fun connect(): Observable<ConnectionResult> {
        return billingSource.connect()
    }

    override fun disconnect(): Completable {
        return billingSource.disconnect()
    }
}