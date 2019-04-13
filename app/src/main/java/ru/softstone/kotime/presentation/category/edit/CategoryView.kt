package ru.softstone.kotime.presentation.category.edit

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.category.model.CategoryGoalType

interface CategoryView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCategoryName(name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showGoalType(categoryGoalType: CategoryGoalType)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun enableNextButton(enabled: Boolean)
}
