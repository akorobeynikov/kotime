package ru.softstone.kotime.domain.statistics

import io.reactivex.Single
import ru.softstone.kotime.domain.statistics.model.StatItem
import java.util.*

interface StatInteractor {
    fun getStatistics(date: Date): Single<List<StatItem>>
}