package ru.softstone.kotime.data.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.ActionSource
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import javax.inject.Inject

class ActionSourceImpl @Inject constructor(private val actionDao: ActionDao) : ActionSource {
    override fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable {
        val action = ActionEntry(0, categoryId, startTime, endTime, description, true)
        return actionDao.insertAll(action)
    }

    override fun observeActions(): Flowable<List<ActionAndCategory>> {
        return actionDao.getAllActive().map { actions ->
            actions.map {
                ActionAndCategory(
                    it.uid,
                    it.categoryName,
                    it.startTime,
                    it.endTime,
                    it.description
                )
            }
        }
    }
}