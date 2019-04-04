package ru.softstone.kotime.presentation.statistics.rv

import android.widget.TextView
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class TotalTimeViewHolder : KotlinEpoxyHolder() {
    val timeView by bind<TextView>(R.id.time_view)
}