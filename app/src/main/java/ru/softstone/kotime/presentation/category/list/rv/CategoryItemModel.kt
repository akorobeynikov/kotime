package ru.softstone.kotime.presentation.category.list.rv

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.softstone.kotime.R

@EpoxyModelClass(layout = R.layout.item_category)
abstract class CategoryItemModel : EpoxyModelWithHolder<CategoryViewHolder>() {
    @EpoxyAttribute
    lateinit var name: String
    @EpoxyAttribute
    lateinit var deleteListener: View.OnClickListener

    override fun bind(holder: CategoryViewHolder) {
        holder.nameView.text = name
        holder.deleteButton.setOnClickListener(deleteListener)
    }
}

