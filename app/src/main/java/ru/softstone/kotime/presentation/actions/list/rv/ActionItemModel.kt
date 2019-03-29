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
    lateinit var deleteClickListener: View.OnClickListener

    override fun bind(holder: ActionViewHolder) {
        holder.descriptionView.text = description
        holder.timeView.text = time
        holder.categoryView.text = category
        holder.deleteButton.setOnClickListener(deleteClickListener)
    }
}

