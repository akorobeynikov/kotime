package ru.softstone.kotime.domain.suggestion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedSuggestion(val description: String, val categoryId: Int) : Parcelable
