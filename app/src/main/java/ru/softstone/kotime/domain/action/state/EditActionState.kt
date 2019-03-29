package ru.softstone.kotime.domain.action.state

import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditActionState(val actionId: Int) : ActionState
