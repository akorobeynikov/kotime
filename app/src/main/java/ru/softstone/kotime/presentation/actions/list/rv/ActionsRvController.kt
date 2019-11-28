package ru.softstone.kotime.presentation.actions.list.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.presentation.actions.list.model.ActionItem
import javax.inject.Inject

class ActionsRvController @Inject constructor() : TypedEpoxyController<List<ActionItem>>() {
    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun buildModels(items: List<ActionItem>) {
        items.forEach {
            actionItem {
                id(it.id.toLong())
                description(it.description)
                time(it.time)
                category(it.category)
                color(it.color)
                itemClickListener { model, _, _, _ -> onItemClickListener?.invoke(model.id().toInt()) }
            }
        }
    }

    fun setItemClickListener(listener: ((Int) -> Unit)) {
        onItemClickListener = listener
    }

}