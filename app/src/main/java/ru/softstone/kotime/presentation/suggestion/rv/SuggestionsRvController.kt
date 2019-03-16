package ru.softstone.kotime.presentation.suggestion.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import javax.inject.Inject

//todo fragment scope?
class SuggestionsRvController @Inject constructor() : TypedEpoxyController<List<Suggestion>>() {
    private var clickListener: ((Int) -> Unit)? = null

    override fun buildModels(items: List<Suggestion>) {
        items.forEach {
            suggestionItem {
                id(it.id)
                category(it.category)
                description(it.description)
                clickListener { model, _, _, _ ->
                    clickListener?.invoke(model.id().toInt())
                }
            }
        }
    }

    fun setOnClickListener(listener: (Int) -> Unit) {
        clickListener = listener
    }
}