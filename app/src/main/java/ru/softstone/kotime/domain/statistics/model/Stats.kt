package ru.softstone.kotime.domain.statistics.model

data class Stats(val categoryStats: List<CategoryStatItem>, val goalStats: List<GoalStatItem>, val totalTime: Long)