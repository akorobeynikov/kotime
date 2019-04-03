package ru.softstone.kotime.presentation.category.edit.behavior

import ru.softstone.kotime.domain.category.model.CategoryGoalType

interface CategoryBehavior {
    fun start()
    fun onNextClick(categoryName: String, categoryGoalType: CategoryGoalType)
    fun onDestroy()
}