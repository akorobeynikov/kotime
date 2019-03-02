package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import ru.softstone.kotime.domain.category.model.Category
import ru.softstone.kotime.domain.time.TimeSource
import javax.inject.Inject

class ActionInteractorImpl @Inject constructor(
    private val actionSource: ActionSource,
    private val timeSource: TimeSource
) : ActionInteractor {
    override fun addAction(description: String, category: Category): Completable {
        val endTime = timeSource.getCurrentTime()
        // todo получить время начала
        return actionSource.addAction(category.id, endTime, endTime, description)
    }
}