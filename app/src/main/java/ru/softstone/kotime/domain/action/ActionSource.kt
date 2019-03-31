package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.action.model.Action
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.action.model.DesriptionAndCategory

interface ActionSource {
    fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable
    fun observeActions(startTime: Long, endTime: Long): Flowable<List<ActionAndCategory>>
    fun getMostFrequent(): Single<List<DesriptionAndCategory>>
    fun getMostFrequentWhere(descriptionStartsWith: String): Single<List<DesriptionAndCategory>>
    fun setStatus(actionId: Int, active: Boolean): Completable
    fun getAction(actionId: Int): Single<Action>
    fun updateActiveAction(action: Action): Completable
}