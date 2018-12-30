package ru.softstone.kotime.architecture.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ActionDao {
    @Query("SELECT * FROM `action`")
    fun getAll(): List<ActionEntry>

    @Query("SELECT * FROM `action` WHERE active=1")
    fun getAllActive(): List<ActionEntry>

    @Insert
    fun insertAll(vararg actions: ActionEntry)

    @Update
    fun update(action: ActionEntry)
}