package ru.softstone.kotime.domain.action

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.softstone.kotime.domain.action.model.ActionAndCategory
import ru.softstone.kotime.domain.time.TimeInteractor
import ru.softstone.kotime.domain.time.TimeSource
import java.util.*
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

    override fun addAction(categoryId: Int, startTime: Long, endTime: Long, description: String): Completable {
        return actionSource.addAction(categoryId, startTime, endTime, description)
            .andThen(timeInteractor.setStartTime(endTime))
    }

    override fun observeActions(date: Date): Flowable<List<ActionAndCategory>> {
        // todo добавить настройки времени смены дня
        // todo вынести получение таймстампов
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (calendar.get(Calendar.HOUR_OF_DAY) < 4) {
            calendar.add(Calendar.DATE, -1)
        }
        calendar.set(Calendar.HOUR_OF_DAY, 4)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        calendar.add(Calendar.DATE, 1)
        val endTime = calendar.timeInMillis
        return actionSource.observeActions(startTime, endTime)
    }
}