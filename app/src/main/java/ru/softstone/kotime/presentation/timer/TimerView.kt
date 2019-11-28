package ru.softstone.kotime.presentation.timer

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.presentation.customview.chart.ChartItem

interface TimerView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTime(seconds: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsRunning(running: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showStopTimerDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showChart(list: List<ChartItem>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTimeMarks(currentTime: Int)
}
