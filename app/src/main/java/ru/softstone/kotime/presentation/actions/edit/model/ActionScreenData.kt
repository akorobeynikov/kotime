package ru.softstone.kotime.presentation.actions.edit.model

import java.util.*

data class ActionScreenData(
    val selectedCategoryId: Int?,
    val description: String?,
    val startTime: Date,
    val endTime: Date
)