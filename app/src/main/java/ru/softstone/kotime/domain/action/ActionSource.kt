package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.action.model.DesriptionAndCategory

interface ActionSource {
    fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable
    fun observeActions(): Flowable<List<ActionAndCategory>>
    fun getMostFrequent(): Single<List<DesriptionAndCategory>>
    fun getMostFrequentWhere(descriptionStartsWith: String): Single<List<DesriptionAndCategory>>
}