package ru.softstone.kotime.domain.action.model

data class ActionAndCategory(
    val uid: Int,
    val categoryName: String,
    val startTime: Long,
    val endTime: Long,
    val description: String
)