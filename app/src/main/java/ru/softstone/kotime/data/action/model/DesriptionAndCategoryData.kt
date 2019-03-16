package ru.softstone.kotime.data.action.model

import androidx.room.ColumnInfo

data class DesriptionAndCategoryData(
    @ColumnInfo(name = "category_id") var categoryId: Int,
    @ColumnInfo(name = "category_name") var categoryName: String,
    @ColumnInfo(name = "description") var description: String
)