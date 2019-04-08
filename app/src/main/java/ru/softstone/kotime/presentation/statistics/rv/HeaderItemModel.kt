package ru.softstone.kotime.presentation.statistics.rv

import androidx.annotation.StringRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R

@EpoxyModelClass(layout = R.layout.item_subheader)
abstract class HeaderItemModel : EpoxyModelWithHolder<SubheaderViewHolder>() {
    @EpoxyAttribute
    @StringRes
    var header: Int = 0

    override fun bind(holder: SubheaderViewHolder) {
        holder.header.setText(header)
    }
}

