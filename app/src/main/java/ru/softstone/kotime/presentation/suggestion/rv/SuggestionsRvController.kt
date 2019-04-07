package ru.softstone.kotime.presentation.suggestion.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.presentation.suggestion.model.Suggestion
import javax.inject.Inject

//todo fragment scope?
class SuggestionsRvController @Inject constructor() : TypedEpoxyController<List<Suggestion>>() {
    private var clickListener: ((Int, String) -> Unit)? = null
    private var editListener: ((Int, String) -> Unit)? = null

    override fun buildModels(items: List<Suggestion>) {
        items.forEach {
            suggestionItem {
                id(it.id)
                category(it.category)
                categoryId(it.categoryId)
                description(it.description)
                clickListener { model, _, _, _ ->
                    clickListener?.invoke(model.categoryId(), model.description())
                }
                editListener { model, _, _, _ ->
                    editListener?.invoke(model.categoryId(), model.description())
                }
            }
        }
    }

    fun setOnClickListener(listener: (Int, String) -> Unit) {
        clickListener = listener
    }

    fun setEditListener(listener: (Int, String) -> Unit) {
        editListener = listener
    }
}