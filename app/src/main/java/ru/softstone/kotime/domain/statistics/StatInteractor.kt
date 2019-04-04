package ru.softstone.kotime.domain.statistics

import io.reactivex.Single
import ru.softstone.kotime.domain.statistics.model.Stats
import java.util.*

interface StatInteractor {
    fun getStatistics(date: Date): Single<Stats>
}