package ru.softstone.kotime.domain.action.model

import ru.softstone.kotime.domain.category.model.CategoryGoalType

data class ActionAndCategory(
    val uid: Int,
    val categoryName: String,
    val goalType: CategoryGoalType,
    val startTime: Long,
    val endTime: Long,
    val description: String
)