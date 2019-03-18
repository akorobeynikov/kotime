package ru.softstone.kotime.presentation.statistics.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.domain.statistics.model.StatItem
import javax.inject.Inject

class StatsRvController @Inject constructor() : TypedEpoxyController<List<StatItem>>() {
    override fun buildModels(items: List<StatItem>) {
        items.forEach {
            statItem {
                id(it.hashCode())
                percentage(it.percentage)
                time(it.duration)
                category(it.categoryName)
            }
        }
    }
}