package ru.softstone.kotime.presentation.statistics.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.domain.statistics.model.Stats
import javax.inject.Inject

class StatsRvController @Inject constructor() : TypedEpoxyController<Stats>() {
    override fun buildModels(stats: Stats) {
        var id = 1L
        totalTimeItem {
            id(id++)
            time(stats.totalTime)
        }
        stats.goalStats.forEach {
            goalStatItem {
                id(id++)
                percentage(it.percentage)
                time(it.duration)
                goal(it.goalType)
            }
        }
        stats.categoryStats.forEach {
            statItem {
                id(id++)
                percentage(it.percentage)
                time(it.duration)
                category(it.categoryName)
            }
        }
    }
}