package ru.softstone.kotime.presentation.log

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_log)
abstract class LogItemModel : EpoxyModelWithHolder<Holder>() {
    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    lateinit var time: String

    override fun bind(holder: Holder) {
        holder.descriptionView.text = description
        holder.timeView.text = time
    }
}

class Holder : KotlinEpoxyHolder() {
    val descriptionView by bind<TextView>(R.id.description_view)
    val timeView by bind<TextView>(R.id.time_view)
}