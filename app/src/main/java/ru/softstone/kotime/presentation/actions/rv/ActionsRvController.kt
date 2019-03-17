package ru.softstone.kotime.presentation.actions.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.presentation.actions.model.ActionItem
import javax.inject.Inject

class ActionsRvController @Inject constructor() : TypedEpoxyController<List<ActionItem>>() {
    override fun buildModels(items: List<ActionItem>) {
        items.forEach {
            actionItem {
                id(it.hashCode())
                description(it.description)
                time(it.time)
                category(it.category)
            }
        }
    }
}