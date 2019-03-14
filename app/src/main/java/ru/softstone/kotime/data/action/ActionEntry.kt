package ru.softstone.kotime.data.action

import androidx.room.*
import ru.softstone.kotime.data.category.CategoryEntry

// todo вынести названия полей в константы
@Entity(
    tableName = "action",
    indices = [Index(value = ["category_id"]), Index(value = ["start_time"]), Index(value = ["end_time"])],
    foreignKeys = [ForeignKey(
        entity = CategoryEntry::class,
        onDelete = ForeignKey.RESTRICT,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("category_id")
    )]
)
data class ActionEntry(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "category_id") var categoryId: Int,
    @ColumnInfo(name = "start_time") var startTime: Long,
    @ColumnInfo(name = "end_time") var endTime: Long,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "active") var active: Boolean
)