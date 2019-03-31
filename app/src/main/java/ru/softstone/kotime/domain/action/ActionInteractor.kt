package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.domain.action.model.Action
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.action.state.ActionState
import java.util.*

interface ActionInteractor {
    fun addAction(description: String, categoryId: Int): Completable
    fun observeActions(date: Date): Flowable<List<ActionAndCategory>>
    fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable
    fun deleteAction(actionId: Int): Completable
    fun setActionState(state: ActionState)
    fun getActionState(): Single<ActionState>
    fun getAction(actionId: Int): Single<Action>
    fun updateActiveAction(action: Action): Completable
}