package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView

interface TimerView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTime(seconds: Int)
}
