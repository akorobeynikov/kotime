package ru.softstone.kotime.presentation.statistics.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.R
import ru.softstone.kotime.domain.statistics.model.Stats
import javax.inject.Inject

class StatsRvController @Inject constructor() : TypedEpoxyController<Stats>() {
    override fun buildModels(stats: Stats) {
        var id = 1L
        totalTimeItem {
            id(id++)
            time(stats.totalTime)
        }
        if (stats.categoryStats.isNotEmpty()) {
            headerItem {
                id(id++)
                header(R.string.category_stats_header)
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
        if (stats.goalStats.isNotEmpty()) {
            headerItem {
                id(id++)
                header(R.string.goal_stats_header)
            }
            stats.goalStats.forEach {
                goalStatItem {
                    id(id++)
                    percentage(it.percentage)
                    time(it.duration)
                    goal(it.goalType)
                }
            }
        }
    }
}