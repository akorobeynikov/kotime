package ru.softstone.kotime.domain.statistics.model

import ru.softstone.kotime.domain.category.model.CategoryGoalType

data class GoalStatItem(val goalType: CategoryGoalType, val duration: Long, val percentage: Int)