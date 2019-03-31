package ru.softstone.kotime.data.action

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.data.action.model.ActionAndCategoryData
import ru.softstone.kotime.data.action.model.ActionEntry
import ru.softstone.kotime.data.action.model.DesriptionAndCategoryData

@Dao
interface ActionDao {
    @Query(
        """
        SELECT `action`.uid,
        `action`.start_time,
        `action`.end_time,
        `action`.description,
        category.name AS category_name
        FROM `action`
        INNER JOIN category ON `action`.category_id = category.uid
        WHERE `action`.active = 1 AND end_time > :startTime AND start_time < :endTime
        """
    )
    fun getActiveBetween(startTime: Long, endTime: Long): Flowable<List<ActionAndCategoryData>>

    @Query(
        """
        SELECT description, category_id, category.name AS category_name
        FROM (
            SELECT uid, description, category_id
            FROM `action`
            WHERE active = 1
            ORDER BY `action`.uid DESC
            LIMIT 500) AS `action`
        INNER JOIN category ON `action`.category_id = category.uid
        GROUP BY category_id, description
        ORDER BY count(*) DESC, `action`.uid DESC
        LIMIT 20
    """
    )
    fun getMostFrequent(): Single<List<DesriptionAndCategoryData>>

    @Query(
        """
        SELECT description, category_id, category.name AS category_name
        FROM (
            SELECT uid, description, category_id
            FROM `action`
            WHERE active = 1
            ORDER BY `action`.uid DESC
            LIMIT 1000) AS `action`
        INNER JOIN category ON `action`.category_id = category.uid
        WHERE `action`.description LIKE :descriptionFilter
        GROUP BY category_id, description
        ORDER BY count(*) DESC, `action`.uid DESC
        LIMIT 10
    """
    )
    fun getMostFrequent(descriptionFilter: String): Single<List<DesriptionAndCategoryData>>

    @Query(" SELECT * FROM `action` WHERE uid = :actionId")
    fun getAction(actionId: Int): Single<ActionEntry>

    @Insert
    fun insertAll(vararg actions: ActionEntry): Completable

    @Update
    fun update(action: ActionEntry): Completable

    @Query("UPDATE `action` SET active = :active WHERE uid = :actionId")
    fun setStatus(actionId: Int, active: Boolean): Completable
}