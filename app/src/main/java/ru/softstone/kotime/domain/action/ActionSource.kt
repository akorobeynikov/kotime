package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.model.ActionAndCategory

interface ActionSource {
    fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable
    fun observeActions(): Flowable<List<ActionAndCategory>>
}