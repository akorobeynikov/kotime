package ru.softstone.kotime.architecture.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "action",
    foreignKeys = [ForeignKey(
        entity = CategoryEntry::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("category_id")
    )]
)
data class ActionEntry(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "category_id") var categoryId: Int,
    @ColumnInfo(name = "start_time") var startTime: Long,
    @ColumnInfo(name = "end_time") var endTime: Long,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "active") var active: Boolean
)