package ru.softstone.kotime.domain.category

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.category.model.AddResult
import ru.softstone.kotime.domain.category.model.Category

interface CategoryInteractor {
    fun addCategory(name: String): Single<AddResult>
    fun observeActiveCategories(): Flowable<List<Category>>
    fun disableCategory(categoryId: Int): Completable
}