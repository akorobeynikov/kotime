package ru.softstone.kotime.presentation.statistics.rv

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R
import ru.softstone.kotime.presentation.getFormattedDuration
import java.util.concurrent.TimeUnit

@EpoxyModelClass(layout = R.layout.item_stat_total)
abstract class TotalTimeItemModel : EpoxyModelWithHolder<TotalTimeViewHolder>() {
    @EpoxyAttribute
    var time: Long = 0

    override fun bind(holder: TotalTimeViewHolder) {
        val context = holder.timeView.context
        val formattedTime = getFormattedDuration(TimeUnit.MILLISECONDS.toSeconds(time).toInt())
        holder.timeView.text = context.getString(R.string.total_time, formattedTime)
    }
}

