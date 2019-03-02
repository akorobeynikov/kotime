package ru.softstone.kotime.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.softstone.kotime.data.action.ActionDao
import ru.softstone.kotime.data.action.ActionEntry
import ru.softstone.kotime.data.category.CategoryDao
import ru.softstone.kotime.data.category.CategoryEntry

@Database(entities = [ActionEntry::class, CategoryEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun actionDao(): ActionDao
}