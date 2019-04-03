package ru.softstone.kotime.domain.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.category.model.CategoryStatus
import ru.softstone.kotime.domain.category.model.PositionOfCategory

interface CategorySource {
    fun addCategory(name: String, goalType: CategoryGoalType): Completable
    fun addCategories(categoryNames: List<String>): Completable
    fun observeActiveCategories(): Flowable<List<Category>>
    fun getCategoryStatusByName(name: String): Single<CategoryStatus>
    fun setStatus(categoryId: Int, active: Boolean): Completable
    fun getCount(): Single<Int>
    fun updateAllPositions(positions: Set<PositionOfCategory>): Completable
    fun getCategoryById(id: Int): Single<Category>
    fun setType(categoryId: Int, type: CategoryGoalType): Completable
    fun getCategoryByName(name: String): Maybe<Category>
}