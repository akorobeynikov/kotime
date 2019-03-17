package ru.softstone.kotime.data.action

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.data.action.model.ActionEntry
import ru.softstone.kotime.domain.action.ActionSource
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.action.model.DesriptionAndCategory
import javax.inject.Inject

class ActionSourceImpl @Inject constructor(private val actionDao: ActionDao) : ActionSource {
    override fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable {
        val action =
            ActionEntry(0, categoryId, startTime, endTime, description, true)
        return actionDao.insertAll(action)
    }

    override fun observeActions(startTime: Long, endTime: Long): Flowable<List<ActionAndCategory>> {
        return actionDao.getActiveBetween(startTime, endTime).map { actions ->
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

    override fun getMostFrequent(): Single<List<DesriptionAndCategory>> {
        return actionDao.getMostFrequent().map { data ->
            data.map { DesriptionAndCategory(it.categoryId, it.categoryName, it.description) }
        }
    }

    override fun getMostFrequentWhere(descriptionStartsWith: String): Single<List<DesriptionAndCategory>> {
        val filter = "$descriptionStartsWith%"
        return actionDao.getMostFrequent(filter).map { data ->
            data.map { DesriptionAndCategory(it.categoryId, it.categoryName, it.description) }
        }
    }
}