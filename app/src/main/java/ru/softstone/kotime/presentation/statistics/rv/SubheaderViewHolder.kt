package ru.softstone.kotime.presentation.statistics.rv

import android.widget.TextView
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class SubheaderViewHolder : KotlinEpoxyHolder() {
    val header by bind<TextView>(R.id.header_view)
}