package ru.softstone.kotime.presentation.actions.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.presentation.customview.chart.ChartItem
import java.util.*

interface ActionsView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showActions(actions: List<ActionAndCategory>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDate(date: Date)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDateDialog(date: Date)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showChart(chatItems: List<ChartItem>)
}
