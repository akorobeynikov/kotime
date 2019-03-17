package ru.softstone.kotime.presentation.actions.rv

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R

@EpoxyModelClass(layout = R.layout.item_log)
abstract class ActionItemModel : EpoxyModelWithHolder<ActionViewHolder>() {
    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    lateinit var time: String

    @EpoxyAttribute
    lateinit var category: String

    override fun bind(holder: ActionViewHolder) {
        holder.descriptionView.text = description
        holder.timeView.text = time
        holder.categoryView.text = category
    }
}

