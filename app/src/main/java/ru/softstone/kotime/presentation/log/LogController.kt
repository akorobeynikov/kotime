package ru.softstone.kotime.presentation.log

import com.airbnb.epoxy.TypedEpoxyController

class LogController : TypedEpoxyController<List<LogItem>>() {
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