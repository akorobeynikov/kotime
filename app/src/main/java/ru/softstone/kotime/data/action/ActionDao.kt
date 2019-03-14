package ru.softstone.kotime.data.action

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ActionDao {
    @Query("SELECT * FROM `action`")
    fun getAll(): List<ActionEntry>

    @Query("SELECT `action`.uid, `action`.start_time, `action`.end_time, `action`.description, category.name AS category_name  FROM `action`, category WHERE `action`.category_id = category.uid")
    fun getAllActive(): Flowable<List<ActionAndCategoryData>>

    @Insert
    fun insertAll(vararg actions: ActionEntry): Completable

    @Update
    fun update(action: ActionEntry)
}