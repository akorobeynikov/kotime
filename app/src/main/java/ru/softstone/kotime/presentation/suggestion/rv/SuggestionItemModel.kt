package ru.softstone.kotime.presentation.suggestion.rv

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R

@EpoxyModelClass(layout = R.layout.item_suggestion)
abstract class SuggestionItemModel : EpoxyModelWithHolder<SuggestionViewHolder>() {
    @EpoxyAttribute
    lateinit var description: String
    @EpoxyAttribute
    lateinit var category: String
    @EpoxyAttribute
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: SuggestionViewHolder) {
        holder.descriptionView.text = description
        holder.categoryView.text = category
        holder.container.setOnClickListener(clickListener)
    }
}

