package ru.softstone.kotime.domain.time

import io.reactivex.Completable
import io.reactivex.Single
import ru.softstone.kotime.domain.time.TimeInteractor.Companion.STOPPED_TIMER_VALUE
import javax.inject.Inject

class TimeInteractorImpl @Inject constructor(private val timeSource: TimeSource) : TimeInteractor {
    override fun stopTimer(): Completable {
        return timeSource.setStartTime(STOPPED_TIMER_VALUE)
    }

    override fun resetTimer(): Completable {
        return timeSource.setStartTime(timeSource.getCurrentTime())
    }

    override fun getTimeFromStart(): Single<Long> {
        return getStartTime().map { startTime ->
            if (startTime == STOPPED_TIMER_VALUE) {
                0
            } else {
                timeSource.getCurrentTime() - startTime
            }
        }
    }

    override fun getStartTime(): Single<Long> {
        return timeSource.getStartTime().flatMap { statTime ->
            return@flatMap if (statTime > getCurrentTime()) {
                resetTimer().toSingleDefault(getCurrentTime())
            } else {
                Single.just(statTime)
            }
        }
    }

    override fun isStopped(): Single<Boolean> {
        return getStartTime().map { startTime -> startTime == STOPPED_TIMER_VALUE }
    }

    override fun getCurrentTime(): Long = timeSource.getCurrentTime()

    override fun setStartTime(time: Long): Completable {
        return timeSource.setStartTime(time)
    }
}