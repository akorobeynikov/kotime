package ru.softstone.kotime.presentation.category.list.rv

import com.airbnb.epoxy.TypedEpoxyController
import ru.softstone.kotime.domain.category.model.Category
import javax.inject.Inject

//todo fragment scope?
class CategoriesRvController @Inject constructor() : TypedEpoxyController<List<Category>>() {
    private var onDeleteClickListener: ((Int) -> Unit)? = null
    private var onClickListener: ((Int) -> Unit)? = null

    override fun buildModels(items: List<Category>) {
        items.forEach {
            categoryItem {
                id(it.id.toLong())
                name(it.name)
                deleteListener { model, _, _, _ ->
                    onDeleteClickListener?.invoke(model.id().toInt())
                }
                clickListener { model, _, _, _ ->
                    onClickListener?.invoke(model.id().toInt())
                }
            }
        }
    }

    fun setOnDeleteClickListener(listener: (Int) -> Unit) {
        onDeleteClickListener = listener
    }

    fun setOnClickListener(listener: (Int) -> Unit) {
        onClickListener = listener
    }
}