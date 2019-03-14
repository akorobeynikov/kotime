package ru.softstone.kotime.data.time

import io.reactivex.Completable
import io.reactivex.Single
import ru.softstone.kotime.data.pref.PrefSource
import ru.softstone.kotime.domain.time.TimeSource
import javax.inject.Inject


class TimeSourceImpl @Inject constructor(private val prefSource: PrefSource) : TimeSource {
    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }

    override fun getStartTime(): Single<Long> {
        return prefSource.getStartTime(defaultValue = TimeSource.STOPPED_TIMER_VALUE)
    }

    override fun setStartTime(time: Long): Completable {
        return prefSource.setStartTime(time)
    }
}