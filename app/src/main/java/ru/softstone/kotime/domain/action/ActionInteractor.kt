package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.model.ActionAndCategory

interface ActionInteractor {
    fun addAction(description: String, categoryId: Int): Completable
    fun observeActions(): Flowable<List<ActionAndCategory>>
}