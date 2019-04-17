package ru.softstone.kotime.presentation.contribute

import android.app.Activity
import com.arellomobile.mvp.InjectViewState
import ru.softstone.kotime.architecture.domain.Logger
import ru.softstone.kotime.architecture.presentation.BasePresenter
import ru.softstone.kotime.domain.billing.BillingInteractor
import ru.softstone.kotime.domain.billing.model.ConnectionResult
import ru.softstone.kotime.domain.billing.model.ConsumptionResponse
import ru.softstone.kotime.domain.billing.model.ItemForPurchase
import ru.softstone.kotime.domain.billing.model.PurchaseResponse
import ru.terrakok.cicerone.Router
import java.lang.ref.WeakReference
import javax.inject.Inject

@InjectViewState
class ContributePresenter @Inject constructor(
    private val billingInteractor: BillingInteractor,
    private val logger: Logger,
    private val router: Router
) : BasePresenter<ContributeView>() {
    private var connected = false
    private var products: List<ItemForPurchase>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        addDisposable(
            billingInteractor.connect()
                .subscribe(
                    {
                        connected = it is ConnectionResult.ConnectionSuccess
                        if (connected) {
                            onBillingClientConnected()
                        } else {
                            logger.error("Can't connect billing client. Result: ${it.result}")
                        }
                    },
                    {
                        connected = false
                        logger.error("Can't connect billing client", it)
                    }
                )
        )
    }

    private fun onBillingClientConnected() {
        addDisposable(
            billingInteractor.getItemsForPurchase()
                .subscribe({
                    products = it
                    if (it.count() == 3) {
                        viewState.showPrices(it[0].price, it[1].price, it[2].price)
                    }
                    logger.debug("items for purchase")
                }, {
                    logger.error("Can't get items for purchase", it)
                })
        )
        addDisposable(
            billingInteractor.queryPurchases()
                .toObservable().flatMapIterable { it }
                .flatMap { billingInteractor.consumeItem(it.purchaseToken) }
                .subscribe({
                    if (it is ConsumptionResponse.ConsumptionSuccess) {
                        logger.debug("ConnectionSuccess")
                    } else {
                        logger.debug("ConnectionFail ${it.result}, token: ${it.outToken}")
                    }
                }, {
                    logger.error("ConnectionFail", it)
                })
        )
    }

    fun onDonateClick(index: Int, activity: WeakReference<Activity>) {
        products?.let { products ->
            if (products.count() == 3) {
                addDisposable(
                    billingInteractor.purchaseItem(products[index].sku, activity)
                        .subscribe({
                            if (it is PurchaseResponse.PurchaseSuccess)
                                logger.debug("PurchaseSuccess ${it.result}")
                            else {
                                logger.error("PurchaseFail  ${it.result}")
                            }
                        }, {
                            logger.error("PurchaseFail", it)
                        })
                )
            }
        }
    }

    fun onBackPressed() {
        router.exit()
    }

}