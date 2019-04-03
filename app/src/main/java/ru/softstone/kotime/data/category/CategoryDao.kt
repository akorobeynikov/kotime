package ru.softstone.kotime.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<CategoryEntry>

    @Query("SELECT * FROM category WHERE active = 1 ORDER BY position ASC")
    fun getAllActive(): Flowable<List<CategoryEntry>>

    @Query("SELECT * FROM category WHERE name = :name")
    fun findByName(name: String): Maybe<CategoryEntry>

    @Query("SELECT * FROM category WHERE uid = :id")
    fun findById(id: Int): Single<CategoryEntry>

    @Insert
    fun insertAll(vararg categories: CategoryEntry): Completable

    @Insert
    fun insertAll(categories: List<CategoryEntry>): Completable

    @Update
    fun update(category: CategoryEntry)

    @Query("UPDATE category SET position = :position WHERE uid = :id")
    fun updatePosition(id: Int, position: Int): Completable


    @Query("UPDATE category SET active = :active WHERE uid = :categoryId")
    fun setStatus(categoryId: Int, active: Boolean): Single<Int>

    @Query("UPDATE category SET goalType = :goalType WHERE uid = :categoryId")
    fun setType(categoryId: Int, goalType: Int): Single<Int>

    @Query("SELECT COUNT(uid) FROM category WHERE active = 1")
    fun getCount(): Single<Int>

    @Query("SELECT COUNT(uid) FROM category WHERE name = :categoryName")
    fun getCount(categoryName: String): Single<Int>
}