package ru.softstone.kotime.presentation.contribute

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView

interface ContributeView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPrices(price1: String, price2: String, price3: String)
}
