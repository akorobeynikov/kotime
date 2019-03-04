package ru.softstone.kotime.presentation.log.rv

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R

@EpoxyModelClass(layout = R.layout.item_log)
abstract class LogItemModel : EpoxyModelWithHolder<LogViewHolder>() {
    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    lateinit var time: String

    override fun bind(holder: LogViewHolder) {
        holder.descriptionView.text = description
        holder.timeView.text = time
    }
}

