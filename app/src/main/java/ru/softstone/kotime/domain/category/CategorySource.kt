package ru.softstone.kotime.domain.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.category.model.CategoryStatus

interface CategorySource {
    fun addCategory(name: String): Completable
    fun observeActiveCategories(): Flowable<List<Category>>
    fun getCategoryStatusByName(name: String): Single<CategoryStatus>
    fun setStatus(categoryId: Int, active: Boolean): Completable
}