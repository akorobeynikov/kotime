package ru.softstone.kotime.domain.category.state

import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditCategoryState(val categoryId: Int) : CategoryState
