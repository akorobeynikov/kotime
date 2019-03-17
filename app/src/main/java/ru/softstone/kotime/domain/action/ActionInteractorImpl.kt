package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.domain.time.TimeSource
import javax.inject.Inject

class ActionInteractorImpl @Inject constructor(
    private val actionSource: ActionSource,
    private val timeSource: TimeSource,
    private val timeInteractor: TimeInteractor
) : ActionInteractor {
    override fun addAction(description: String, categoryId: Int): Completable {
        val endTime = timeSource.getCurrentTime()
        return timeInteractor.getStartTime().flatMapCompletable { startTime ->
            if (startTime == TimeInteractor.STOPPED_TIMER_VALUE) {
                throw IllegalStateException("Start timer first")
            } else {
                actionSource.addAction(categoryId, startTime, endTime, description)
                    .andThen(timeInteractor.resetTimer())
            }
        }
    }

    override fun observeActions(): Flowable<List<ActionAndCategory>> {
        return actionSource.observeActions()
    }
}