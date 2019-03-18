package ru.softstone.kotime.presentation.statistics

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.statistics.model.StatItem
import java.util.*

interface StatView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showStats(stats: List<StatItem>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDate(date: Date)
}
