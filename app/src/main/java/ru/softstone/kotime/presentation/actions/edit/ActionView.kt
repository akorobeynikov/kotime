package ru.softstone.kotime.presentation.actions.edit

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import java.util.*

interface ActionView : BaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCategories(categories: List<String>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setDescription(description: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showStartTime(date: Date)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEndTime(date: Date)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDuration(seconds: Int, correctionSeconds: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSelectedCategory(categoryName: String)
}
