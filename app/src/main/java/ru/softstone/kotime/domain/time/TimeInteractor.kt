package ru.softstone.kotime.domain.time

import io.reactivex.Completable
import io.reactivex.Single

interface TimeInteractor {
    companion object {
        const val STOPPED_TIMER_VALUE = TimeSource.STOPPED_TIMER_VALUE
    }

    fun stopTimer(): Completable
    fun resetTimer(): Completable
    fun getTimeFromStart(): Single<Long>
    fun getStartTime(): Single<Long>
    fun isStopped(): Single<Boolean>
    fun getCurrentTime(): Long
    fun setStartTime(time: Long): Completable
}