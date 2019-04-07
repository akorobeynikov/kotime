package ru.softstone.kotime.presentation.category.list.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class CategoryViewHolder : KotlinEpoxyHolder() {
    val nameView by bind<TextView>(R.id.name_view)
    val goalView by bind<ImageView>(R.id.goal_view)
    val container by bind<View>(R.id.container)
}