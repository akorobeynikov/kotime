package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import java.util.*

interface ActionInteractor {
    fun addAction(description: String, categoryId: Int): Completable
    fun observeActions(date: Date): Flowable<List<ActionAndCategory>>
    fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable
    fun deleteAction(actionId: Int): Completable
}