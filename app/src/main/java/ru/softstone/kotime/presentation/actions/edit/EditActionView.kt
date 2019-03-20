package ru.softstone.kotime.presentation.actions.edit

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import java.util.*

interface EditActionView : BaseView {
    //todo сохранять выбранную пользователм категорию при повороте экрана
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSelectedCategory(index: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCategories(categories: List<String>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setDescription(description: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showStartTime(date: Date)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEndTime(date: Date)
}
