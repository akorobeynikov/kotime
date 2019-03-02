package ru.softstone.kotime.data.action

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable

@Dao
interface ActionDao {
    @Query("SELECT * FROM `action`")
    fun getAll(): List<ActionEntry>

    @Query("SELECT * FROM `action` WHERE active=1")
    fun getAllActive(): List<ActionEntry>

    @Insert
    fun insertAll(vararg actions: ActionEntry): Completable

    @Update
    fun update(action: ActionEntry)
}