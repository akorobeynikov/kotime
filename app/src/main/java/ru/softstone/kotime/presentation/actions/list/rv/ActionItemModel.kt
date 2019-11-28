package ru.softstone.kotime.presentation.actions.list.rv

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R

@EpoxyModelClass(layout = R.layout.item_action)
abstract class ActionItemModel : EpoxyModelWithHolder<ActionViewHolder>() {
    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    lateinit var time: String

    @EpoxyAttribute
    lateinit var category: String

    @EpoxyAttribute
    var color: Int = 0

    @EpoxyAttribute
    lateinit var itemClickListener: View.OnClickListener

    override fun bind(holder: ActionViewHolder) {
        holder.descriptionView.text = description
        holder.timeView.text = time
        holder.categoryView.text = category
        holder.container.setOnClickListener(itemClickListener)
        holder.colorView.setBackgroundColor(color)
    }
}

