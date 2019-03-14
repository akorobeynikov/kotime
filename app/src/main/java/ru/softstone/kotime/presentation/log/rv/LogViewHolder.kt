package ru.softstone.kotime.presentation.log.rv

import android.widget.TextView
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class LogViewHolder : KotlinEpoxyHolder() {
    val descriptionView by bind<TextView>(R.id.description_view)
    val timeView by bind<TextView>(R.id.time_view)
    val categoryView by bind<TextView>(R.id.category_view)
}