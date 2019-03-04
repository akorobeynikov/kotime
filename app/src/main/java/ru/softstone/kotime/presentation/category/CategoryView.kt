package ru.softstone.kotime.presentation.category

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.softstone.kotime.architecture.presentation.BaseView
import ru.softstone.kotime.domain.category.model.Category

interface CategoryView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCategories(categories: List<Category>)
}
