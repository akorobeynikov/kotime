package ru.softstone.kotime.data.time

import ru.softstone.kotime.domain.time.TimeSource
import javax.inject.Inject


class TimeSourceImpl @Inject constructor() : TimeSource {
    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}