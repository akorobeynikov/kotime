package ru.softstone.kotime.presentation.statistics.rv

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R
import ru.softstone.kotime.presentation.getFormattedDuration
import java.util.concurrent.TimeUnit

@EpoxyModelClass(layout = R.layout.item_stat)
abstract class StatItemModel : EpoxyModelWithHolder<StatViewHolder>() {
    @EpoxyAttribute
    var percentage: Int = 0

    @EpoxyAttribute
    var time: Long = 0

    @EpoxyAttribute
    lateinit var category: String

    override fun bind(holder: StatViewHolder) {
        val context = holder.categoryView.context
        holder.percentageView.text = context.getString(R.string.precentage, percentage)
        holder.timeView.text = getFormattedDuration(TimeUnit.MILLISECONDS.toSeconds(time).toInt())
        holder.categoryView.text = category
    }
}

