package ru.softstone.kotime.data.action

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.softstone.kotime.data.action.model.ActionEntry
import ru.softstone.kotime.domain.action.ActionSource
import ru.softstone.kotime.domain.action.model.Action
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.action.model.DesriptionAndCategory
import ru.softstone.kotime.domain.category.model.CategoryGoalType
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
                    CategoryGoalType.getById(it.goalTypeId),
                    it.startTime,
                    it.endTime,
                    it.description,
                    it.color
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

    override fun setStatus(actionId: Int, active: Boolean): Completable {
        return actionDao.setStatus(actionId, active)
    }

    override fun getAction(actionId: Int): Single<Action> {
        return actionDao.getAction(actionId)
            .map { Action(it.uid, it.categoryId, it.startTime, it.endTime, it.description) }
    }

    override fun updateActiveAction(action: Action): Completable {
        return actionDao.update(
            ActionEntry(
                action.uid,
                action.categoryId,
                action.startTime,
                action.endTime,
                action.description,
                active = true
            )
        )
    }

    override fun getOverlapCount(exceptActionId: Int?, startTime: Long, endTime: Long): Single<Int> {
        return if (exceptActionId != null) {
            actionDao.getOverlapCount(exceptActionId, startTime, endTime)
        } else {
            actionDao.getOverlapCount(startTime, endTime)
        }
    }

}