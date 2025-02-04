package ru.softstone.kotime.presentation.actions.list.rv

import android.view.View
import android.widget.TextView
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class ActionViewHolder : KotlinEpoxyHolder() {
    val container by bind<View>(R.id.container)
    val descriptionView by bind<TextView>(R.id.description_view)
    val timeView by bind<TextView>(R.id.time_view)
    val categoryView by bind<TextView>(R.id.category_view)
    val colorView by bind<View>(R.id.category_color_view)
}