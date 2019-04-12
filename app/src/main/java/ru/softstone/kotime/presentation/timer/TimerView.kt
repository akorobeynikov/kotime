package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView

interface TimerView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTime(seconds: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsRunning(running: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showStopTimerDialog()
}
