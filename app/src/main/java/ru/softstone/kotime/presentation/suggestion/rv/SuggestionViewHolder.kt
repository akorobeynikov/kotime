package ru.softstone.kotime.presentation.suggestion.rv

import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import ru.softstone.kotime.R
import ru.softstone.kotime.architecture.presentation.epoxy.KotlinEpoxyHolder

class SuggestionViewHolder : KotlinEpoxyHolder() {
    val descriptionView by bind<TextView>(R.id.description_view)
    val categoryView by bind<TextView>(R.id.category_view)
    val container by bind<View>(R.id.container)
    val fastRecordButton by bind<MaterialButton>(R.id.fast_record_button)
}