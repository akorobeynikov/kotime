package ru.softstone.kotime.presentation.statistics.rv

import android.widget.TextView
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class StatViewHolder : KotlinEpoxyHolder() {
    val timeView by bind<TextView>(R.id.time_view)
    val categoryView by bind<TextView>(R.id.category_view)
    val percentageView by bind<TextView>(R.id.percentage_view)
}