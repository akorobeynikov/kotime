package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.category.model.Category

interface ActionInteractor {
    fun addAction(description: String, category: Category): Completable
    fun observeActions(): Flowable<List<ActionAndCategory>>
}