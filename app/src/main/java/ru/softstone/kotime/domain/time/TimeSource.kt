package ru.softstone.kotime.domain.time

import io.reactivex.Completable
import io.reactivex.Single

interface TimeSource {
    companion object {
        const val STOPPED_TIMER_VALUE = -1L
    }
    fun getCurrentTime(): Long
    fun getStartTime(): Single<Long>
    fun setStartTime(time: Long): Completable
}