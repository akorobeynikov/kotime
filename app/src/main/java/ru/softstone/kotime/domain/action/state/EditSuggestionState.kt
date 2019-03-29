package ru.softstone.kotime.domain.action.state

import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditSuggestionState(val description: String, val categoryId: Int) : ActionState
