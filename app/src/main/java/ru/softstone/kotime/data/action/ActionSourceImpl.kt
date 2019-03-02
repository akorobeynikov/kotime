package ru.softstone.kotime.data.action

import io.reactivex.Completable
import ru.softstone.kotime.domain.action.ActionSource
import javax.inject.Inject

class ActionSourceImpl @Inject constructor(private val actionDao: ActionDao) : ActionSource {
    override fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable {
        val action = ActionEntry(0, categoryId, startTime, endTime, description, true)
        return actionDao.insertAll(action)
    }
}