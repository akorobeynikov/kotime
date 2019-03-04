package ru.softstone.kotime.presentation.log.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.presentation.log.model.LogItem
import javax.inject.Inject

class LogsRvController @Inject constructor() : TypedEpoxyController<List<LogItem>>() {
    override fun buildModels(items: List<LogItem>) {
        items.forEach {
            logItem {
                id(it.hashCode())
                description(it.description)
                time(it.time)
            }
        }
    }
}