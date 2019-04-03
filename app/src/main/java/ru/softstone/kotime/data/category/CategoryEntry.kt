package ru.softstone.kotime.data.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "category",
    indices = [Index(value = ["name"], unique = true)]
)
data class CategoryEntry(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "goalType") var goalType: Int,
    @ColumnInfo(name = "position") var position: Int,
    @ColumnInfo(name = "active") var active: Boolean
)