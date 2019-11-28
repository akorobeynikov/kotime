package ru.softstone.kotime.domain.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.category.model.AddResult
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryGoalType
import ru.softstone.kotime.domain.category.model.PositionOfCategory
import ru.softstone.kotime.domain.category.state.CategoryState

interface CategoryInteractor {
    fun addCategory(name: String, goalType: CategoryGoalType, color: Int): Single<AddResult>
    fun observeActiveCategories(): Flowable<List<Category>>
    fun disableCategory(categoryId: Int): Completable
    fun updateAllPositions(positions: Set<PositionOfCategory>): Completable
    fun setCategoryState(state: CategoryState)
    fun getCategoryState(): Single<CategoryState>
    fun getCategoryById(categoryId: Int): Single<Category>
    fun updateActiveCategory(category: Category): Completable
}