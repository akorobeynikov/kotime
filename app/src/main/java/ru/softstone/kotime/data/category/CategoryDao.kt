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

    @Query("SELECT * FROM category WHERE active = 1")
    fun getAllActive(): Flowable<List<CategoryEntry>>

    @Query("SELECT * FROM category WHERE name = :name")
    fun findByName(name: String): Maybe<CategoryEntry>

    @Insert
    fun insertAll(vararg categories: CategoryEntry): Completable

    @Update
    fun update(category: CategoryEntry)

    @Query("UPDATE category SET active = :active WHERE uid = :categoryId")
    fun setStatus(categoryId: Int, active: Boolean): Single<Int>
}