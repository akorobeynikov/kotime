package ru.softstone.kotime.domain.action.model

data class Action(
    val uid: Int,
    val categoryId: Int,
    val startTime: Long,
    val endTime: Long,
    val description: String
)