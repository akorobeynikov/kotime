package ru.softstone.kotime.data.action.model

import androidx.room.ColumnInfo

data class ActionAndCategoryData(
    @ColumnInfo(name = "uid") var uid: Int,
    @ColumnInfo(name = "category_name") var categoryName: String,
    @ColumnInfo(name = "start_time") var startTime: Long,
    @ColumnInfo(name = "end_time") var endTime: Long,
    @ColumnInfo(name = "description") var description: String
)