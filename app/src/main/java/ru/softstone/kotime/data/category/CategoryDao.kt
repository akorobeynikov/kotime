package ru.softstone.kotime.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<CategoryEntry>

    @Insert
    fun insertAll(vararg categories: CategoryEntry)

    @Update
    fun update(category: CategoryEntry)
}