package ru.softstone.kotime.domain.billing

import android.app.Activity
import com.android.billingclient.api.Purchase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.softstone.kotime.domain.billing.model.ConnectionResult
import ru.softstone.kotime.domain.billing.model.ConsumptionResponse
import ru.softstone.kotime.domain.billing.model.ItemForPurchase
import ru.softstone.kotime.domain.billing.model.PurchaseResponse
import java.lang.ref.WeakReference

interface BillingInteractor {
    fun disconnect(): Completable
    fun connect(): Observable<ConnectionResult>
    fun purchaseItem(skuId: String, activity: WeakReference<Activity>): Observable<PurchaseResponse>
    fun getItemsForPurchase(): Observable<List<ItemForPurchase>>
    fun queryPurchases(): Single<List<Purchase>>
    fun consumeItem(purchaseToken: String): Observable<ConsumptionResponse>
}