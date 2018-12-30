package ru.softstone.kotime.architecture.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ActionEntry::class, CategoryEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun actionDao(): ActionDao
}